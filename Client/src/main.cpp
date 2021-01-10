#include <stdlib.h>
#include <connectionHandler.h>
#include <iostream>
#include <thread>

/**
* This code assumes that the server replies the exact text the client sent it (as opposed to the practical session example)
*/
void inThread(ConnectionHandler& connectionHandler){
    while(true){
        const short bufsize = 1024;
        char buf[bufsize];
        std::cin.getline(buf, bufsize);
        std::string line(buf);
        if (!connectionHandler.sendMessage(line)) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }
    }
}

int main (int argc, char *argv[]) {
    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }
    std::string host = argv[1];
    short port = atoi(argv[2]);
    
    ConnectionHandler connectionHandler(host, port);
    if (!connectionHandler.connect()) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        return 1;
    }

    std::thread t(inThread, std::ref(connectionHandler));
	
	//From here we will see the rest of the ehco client implementation:
    bool flag = true;
    while (flag) {

        bool ack;
        short opcode;
        std::string message;

        //If there is an error
        if (!connectionHandler.getMessage(ack, opcode, message)) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            flag = false;
        }
        
        if(ack){
            std::cout << "ACK " << opcode;
        }
        else{
            std::cout << "NACK " << opcode;
        }

        if(ack)
            std::cout << " " << message;

        if(ack && opcode == 4)
            flag = false;
    }

    return 0;
}
