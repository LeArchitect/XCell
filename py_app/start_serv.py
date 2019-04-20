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

if __name__ == '__main__':
    xcell = XCellServer()
    xcell.start_service({'payload':'xcell'})
    print('Service started')
    try:
        while True:
            print(xcell.sock.accept())
            sleep(1)
    except KeyboardInterrupt:
        pass
    finally:
        xcell.stop_service()        