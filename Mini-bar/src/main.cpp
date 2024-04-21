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
  String in = terminal->readLine();
  if(in!=""){
    Serial.printf("Command: %s\n",in.c_str());
    if(in.substring(0,7) == "Context"){
      String s = sensors->generateContext();
      terminal->writeString(s);
      Serial.print(s);
      delay(2000);
    }
    else if(in.substring(0,5) == "Sonar"){
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