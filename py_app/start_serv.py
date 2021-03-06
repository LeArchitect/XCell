''' pip install pyzeroconf'''

import logging
import socket
import sys
from time import sleep

from zeroconf import ServiceInfo, Zeroconf

NEWLINE = '\n'



class XCellServer(object):
    def __init__(self):
        self.status = None
        self.addr = socket.gethostbyname(socket.gethostname())
        # self.addr = "192.168.43.80"
        self.sock = socket.socket(family=socket.AF_INET, type=socket.SOCK_STREAM)
        self.sock.bind((self.addr, 0))
        self.port = self.sock.getsockname()[1]
        self.zeroconf = Zeroconf()
        self.info = None
        self.connections = {}
        print(self.addr,self.port)

    def start_service(self, desc):
        self.info = ServiceInfo(
            '_xcell._tcp.local.',
            '_Light._xcell._tcp.local.',
            socket.inet_aton(self.addr),
            self.port, 
            0, 
            0,
            desc, 
            'tuomas.local.')

        self.zeroconf.register_service(self.info, allow_name_change=True)
        self.sock.listen(5)

        
    def stop_service(self):
        self.zeroconf.unregister_service(self.info)
        self.zeroconf.close()

    def accept_connection_req(self):
        conn, addr = self.sock.accept()
        self.connections[addr] = conn
        print('Added new connection')
        return conn, addr
    
    def receive_msgs(self):
        for addr, conn in self.connections.items():
            data = conn.recv(128)
            if len(data) > 0:
                yield(addr, data)
                
                

    def send_status(self, addr, msg=None):
        if msg is None:
            msg = self.status
        print('Sent message: ' + msg + ' To: ' + addr[0])
        msg += NEWLINE
        try:
            self.connections[addr].sendall(msg.encode())
        except ConnectionAbortedError:
            return
    def resp_to_msgs(self):
        for addr, data in self.receive_msgs():
            print(data)
            if data == b'ON':
                self.status = 'ON'
                self.send_status(addr)
            elif data == b'OFF':
                self.status = 'OFF'
                self.send_status(addr)
            elif data == b'STATUS':
                self.send_status(addr)
            else:
                self.send_status(addr, "UNKNOWN_MSG_TYPE")
                

if __name__ == '__main__':
    xcell = XCellServer()
    xcell.start_service({"type":"lightswitch"})
    print('Service started')
    xcell.accept_connection_req()
    try:
        while True:
            xcell.resp_to_msgs()
            sleep(1)
    except KeyboardInterrupt:
        pass
    finally:
        xcell.stop_service()        
