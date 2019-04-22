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
            '_xcell._xcell._tcp.local.',
            socket.inet_aton(self.addr),
            self.port, 
            0, 
            0,
            desc, 
            'tuomas.local.')

        self.zeroconf.register_service(self.info)
        self.sock.listen(5)

        
    def stop_service(self):
        self.zeroconf.unregister_service(self.info)
        self.zeroconf.close()

    def accept_connection_req(self):
        conn, addr = self.sock.accept()
        host, _ = addr
        self.connections[host] = conn
        print('Added new connection')
        return conn, addr
    
    def receive_msgs(self):
        for host, conn in self.connections.items():
            print("Data incoming")
            data = conn.recv(128)
            msg = "OK\n"
            try:
                conn.sendall(msg.encode())
            except ConnectionAbortedError:
                return
            yield(host, data)
    def send_status(self, msg = "OK\n"):
        for host, conn in self.connections.items():
            print('Sent message: ' + msg.strip('\n') + ' To: ' + host)
            try:
                conn.sendall(msg.encode())
            except ConnectionAbortedError:
                return


if __name__ == '__main__':
    xcell = XCellServer()
    xcell.start_service({"type":"lightcontrol"})
    print('Service started')
    xcell.accept_connection_req()
    try:
        while True:
            xcell.send_status()    
            sleep(1)
    except KeyboardInterrupt:
        pass
    finally:
        xcell.stop_service()        