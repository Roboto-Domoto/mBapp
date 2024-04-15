#ifndef __BLUETOOTH_TERMINAL_H
#define __BLUETOOTH_TERMINAL_H
#include <BluetoothSerial.h>

#if !defined(CONFIG_BT_ENABLED) || !defined(CONFIG_BLUEDROID_ENABLED)
#error Bluetooth is not enabled! Please run `make menuconfig` to and enable it
#endif

#if !defined(CONFIG_BT_SPP_ENABLED)
#error Serial Bluetooth not available or not enabled. It is only available for the ESP32 chip.
#endif

class BluetoothTerminal{
    BluetoothSerial serial;
public:
    BluetoothTerminal(
        const char* deviceName,
        bool usePin=false,
        const char* pin="1234"
    );
    String readLine();
    char readChar();
    void writeString(String message);
    void writeChar(char c);
};

#endif

