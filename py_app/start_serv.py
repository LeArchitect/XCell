''' pip install pyzeroconf'''

import logging
import socket
import sys
from time import sleep

from zeroconf import ServiceInfo, Zeroconf


class XCellServer(object):
    def __init__(self):
        self.addr = socket.gethostbyname(socket.gethostname())
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
            #socket.inet_aton(self.addr),
            socket.inet_aton("37.219.114.6"),
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
            print("Data incoming")
            data = conn.recv(128)
            if len(data) > 0:
                yield(addr, data)
                
                

    def send_status(self, addr, msg = "OK\n"):
        print('Sent message: ' + msg.strip('\n') + ' To: ' + addr[0])
        try:
            self.connections[addr].sendall(msg.encode())
        except ConnectionAbortedError:
            return


if __name__ == '__main__':
    xcell = XCellServer()
    xcell.start_service({"type":"lightcontrol"})
    print('Service started')
    xcell.accept_connection_req()
    try:
        while True:
            msgs = list(xcell.receive_msgs())
            for addr, data in msgs:
                print(data)
                if data == b'STATUS':
                    xcell.send_status(addr, "OK\n")
                else:
                    xcell.send_status(addr, "UNKNOWN REQUEST\n")
            sleep(1)
    except KeyboardInterrupt:
        pass
    finally:
        xcell.stop_service()        
