#include <Arduino.h>
#include <structures/Sensors.h>
#include <structures/BluetoothTerminal.h>

#define DHT_TOP 2
#define DHT_DOOR 4
#define DHT_BOTTOM 5
#define BUMPER 12 
#define PRESSURE_TOP 13
#define PRESSURE_BOTTOM 14

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
  String in = terminal->readLine();
  if(in!=""){
    Serial.printf("Command: %s\n",in.c_str());
    if(in.charAt(0) == 'C'){
      String s = sensors->generateContext();
      terminal->writeString(s);
      Serial.print(s);
      delay(2000);
    }
    else if(in.charAt(0) == 'S'){
      int initialTopValue = sensors->getPressure(true);
      int initialBottomValue = sensors->getPressure(false);
      String in = terminal->readLine();
      while(in!="END"){
        int newTopValue = sensors->getPressure(true);
        int newBottomValue = sensors->getPressure(false);
        if(initialTopValue<newTopValue-PRESSURE_ERROR){
          terminal->writeString("ITL");
          break;
        }else if(initialBottomValue<newBottomValue+PRESSURE_ERROR){
          terminal->writeString("IBL");
          break;
        }else if(initialTopValue>newTopValue-PRESSURE_ERROR){
          terminal->writeString("ITM");
          break;
        }else if(initialBottomValue>newBottomValue+PRESSURE_ERROR){
          terminal->writeString("IBM");
          break;
        }
        delay(500);
        in = terminal->readLine();
      }
    }else{
      Serial.println("Command not found");
    }
  }
  delay(1000);
}