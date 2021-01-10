#include <connectionHandler.h>
#include <boost/lexical_cast.hpp>
 
using boost::asio::ip::tcp;

using std::cin;
using std::cout;
using std::cerr;
using std::endl;
using std::string;
 
ConnectionHandler::ConnectionHandler(string host, short port): host_(host), port_(port), io_service_(), socket_(io_service_){}
    
ConnectionHandler::~ConnectionHandler() {
    close();
}
 
bool ConnectionHandler::connect() {
    try {
		tcp::endpoint endpoint(boost::asio::ip::address::from_string(host_), port_); // the server endpoint
		boost::system::error_code error;
		socket_.connect(endpoint, error);
		if (error)
			throw boost::system::system_error(error);
    }
    catch (std::exception& e) {
        std::cerr << "Connection failed (Error: " << e.what() << ')' << std::endl;
        return false;
    }
    return true;
}
 
bool ConnectionHandler::getBytes(char bytes[], unsigned int bytesToRead) {
    size_t tmp = 0;
	boost::system::error_code error;
    try {
        while (!error && bytesToRead > tmp ) {
			tmp += socket_.read_some(boost::asio::buffer(bytes+tmp, bytesToRead-tmp), error);			
        }
		if(error)
			throw boost::system::system_error(error);
    } catch (std::exception& e) {
        std::cerr << "recv failed (Error: " << e.what() << ')' << std::endl;
        return false;
    }
    return true;
}

bool ConnectionHandler::sendBytes(const char bytes[], int bytesToWrite) {
    int tmp = 0;
	boost::system::error_code error;
    try {
        while (!error && bytesToWrite > tmp ) {
			tmp += socket_.write_some(boost::asio::buffer(bytes + tmp, bytesToWrite - tmp), error);
        }
		if(error)
			throw boost::system::system_error(error);
    } catch (std::exception& e) {
        std::cerr << "recv failed (Error: " << e.what() << ')' << std::endl;
        return false;
    }
    return true;
}
 
bool ConnectionHandler::getMessage(bool& ack, short& opcode, std::string& message){
    short recOpCode = 0;
    char b = 0;

    message = "";

    //Getting message opcode
    if(!getBytes(&b, 1))
        return false;
    recOpCode = (short)(0x100 * (short)b);
    if(!getBytes(&b, 1))
        return false;
    recOpCode += (short)(b & 0xFF);

    //Getting embedded opcode
    if(!getBytes(&b, 1))
        return false;
    opcode = (short)(0x100 * (short)b);
    if(!getBytes(&b, 1))
        return false;
    opcode += (short)(b & 0xFF);

    //If receiving ACK
    if(recOpCode == 12){
        ack = true;
        bool flag = true;
        while(flag){
            if(!getBytes(&b, 1))
                return false;
            if(b != '\0'){
                message += b;
            }
            else{
                flag = false;
            }
        }
    }
    else{
        ack = false;
    }

    return true;
}

bool ConnectionHandler::sendMessage(std::string userInput){
    char arrToSend[128];
    int indexInArr = 0;
    int fieldIndex = -1;

    short msgOpCode;

    size_t pos = 0;
    std::string substr;
    bool continueFlag = true;
    while(continueFlag){
        continueFlag = (pos = userInput.find(" ")) != std::string::npos;
        substr = userInput.substr(0, pos);

        //Encoding opcode
        if(fieldIndex == -1){
            if(substr == "ADMINREG"){ msgOpCode = 1; }
            else if(substr == "STUDENTREG"){ msgOpCode = 2; }
            else if(substr == "LOGIN"){ msgOpCode = 3; }
            else if(substr == "LOGOUT"){ msgOpCode = 4; }
            else if(substr == "COURSEREG"){ msgOpCode = 5; }
            else if(substr == "KDAMCHECK"){ msgOpCode = 6; }
            else if(substr == "COURSESTAT"){ msgOpCode = 7; }
            else if(substr == "STUDENTSTAT"){ msgOpCode = 8; }
            else if(substr == "ISREGISTERED"){ msgOpCode = 9; }
            else if(substr == "UNREGISTER"){ msgOpCode = 10; }
            else if(substr == "MYCOURSES"){ msgOpCode = 11; }
            else { msgOpCode = 0; }

            indexInArr = encodeShort(arrToSend, indexInArr, msgOpCode);
        }
        else{
            if(msgOpCode == 1 || msgOpCode == 2 || msgOpCode == 3){
                if(fieldIndex == 0 || fieldIndex == 1){
                    indexInArr = encodeString(arrToSend, indexInArr, substr);
                }
            }
            if(msgOpCode == 5 || msgOpCode == 6 || msgOpCode == 7 || msgOpCode == 9 || msgOpCode == 10){
                if(fieldIndex == 0){
                    short toSend = boost::lexical_cast<short>(substr);
                    indexInArr = encodeShort(arrToSend, indexInArr, toSend);
                }
            }
            if(msgOpCode == 8){
                if(fieldIndex == 0){
                    indexInArr = encodeString(arrToSend, indexInArr, substr);
                }
            }
        }

        userInput.erase(0, pos + 1);
        fieldIndex += 1;
    }

    return sendBytes(arrToSend, indexInArr);
}
 
 
int ConnectionHandler::encodeShort(char* buff, int index, short toEncode){
    buff[index] = (char)((toEncode & (unsigned short)0xFF00)>>8);
    buff[index + 1] = (char)((unsigned short)toEncode & (unsigned short)0x00FF);
    return index + 2;
}

int ConnectionHandler::encodeString(char* buff, int index, std::string toEncode){
    for(int i = 0; i < (int)toEncode.size(); ++i){
        buff[index + i] = toEncode[i];
    }

    buff[index + toEncode.size()] = '\0';
    return index + toEncode.size() + 1;
}
 
// Close down the connection properly.
void ConnectionHandler::close() {
    try{
        socket_.close();
    } catch (...) {
        std::cout << "closing failed: connection already closed" << std::endl;
    }
}
