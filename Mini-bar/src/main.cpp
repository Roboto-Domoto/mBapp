#include <Arduino.h>
#include <structures/Sensors.h>
#include <structures/BluetoothTerminal.h>

#define DHT_TOP 4
#define DHT_DOOR 16
#define DHT_BOTTOM 17
#define BUMPER 5 
#define PRESSURE_TOP 2
#define PRESSURE_BOTTOM 15

#define PRESSURE_ERROR 30

Sensors* sensors;
BluetoothTerminal* terminal;

void setup() {
  //Inicializacion monitor serial:
  Serial.begin(115200);
  
  //Creación de los objetos base:
  sensors = new Sensors(DHT_TOP,DHT_DOOR,
                        DHT_BOTTOM,BUMPER,
                        PRESSURE_TOP,PRESSURE_BOTTOM);
  terminal = new BluetoothTerminal("ESP32-BT-MINIBAR");
}

void loop() {
  String context = sensors->generateContext();
  terminal->writeString(context);
  Serial.println(context);
  delay(2000);
}
