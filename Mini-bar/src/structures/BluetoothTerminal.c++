#include <structures/BluetoothTerminal.h>

BluetoothTerminal::BluetoothTerminal(
    const char* deviceName,
    bool usePin,
    const char* pin
){
    this->serial.begin(deviceName);
    Serial.printf(
        "\nThe device with name \"%s\" is started.\nNow you can pair it with Bluetooth!\n", 
        deviceName
    );
    if(usePin){
        this->serial.setPin(pin);
        Serial.println("Using PIN");
    }
}

String BluetoothTerminal::readLine(){
    if (this->serial.available()) {
        String line = "";
        char in = this->serial.read();
        while(in!='\n'){
            line+=in;
            in = this->serial.read();
        }
        Serial.print("Line: ");
        Serial.println(line);
        return line;
    }
    return "";
}

char BluetoothTerminal::readChar(){
    if (this->serial.available()) {
        char in = this->serial.read();
        Serial.print("Char: ");
        Serial.println(in);
        return in;
    }
    return '\n';
}

void BluetoothTerminal::writeString(String message){
    if(this->serial.available()) {
        this->serial.print(message);
    }else{
        Serial.print("N");
    }
}

void BluetoothTerminal::writeChar(char c){
    if(this->serial.available()) {
        this->serial.print(c);
    }
}