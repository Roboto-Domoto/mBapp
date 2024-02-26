#include <Arduino.h>
#include <BluetoothSerial.h>

BluetoothSerial b;

void setup() {
  Serial.begin(115200);
  b.begin("MyBluetooth");
}

void loop() {
}