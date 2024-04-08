#include <Arduino.h>
#include <structures/Sensors.h>
#include <structures/BluetoothTerminal.h>

void loopSecondCore(void *pvParameters);
void printMsg(const char *msg);

#define DHT_TOP 2
#define DHT_DOOR 4
#define DHT_BOTTOM 5
#define BUMPER 12
#define PRESSURE_TOP 13
#define PRESSURE_BOTTOM 14

Sensors* sensors;
BluetoothTerminal* terminal;
TaskHandle_t* taskHandle;

void setup() {
  //Inicializacion monitor serial:
  Serial.begin(115200);
  
  //Creación de los objetos base:
  sensors = new Sensors(DHT_TOP,DHT_DOOR,DHT_BOTTOM,BUMPER,PRESSURE_TOP,PRESSURE_BOTTOM);
  terminal = new BluetoothTerminal("ESP32-BT-MINIBAR");

	//Creacion tarea segundo hilo:
  //xTaskCreatePinnedToCore(loopSecondCore,"SecondaryCore",1000,NULL,1,taskHandle,0);
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
    else{
      int initialTopValue = sensors->getPressure(true);
      int initialBottomValue = sensors->getPressure(false);
      String in = terminal->readLine();
      while(in!="END"){
        int newTopValue = sensors->getPressure(true);
        int newBottomValue = sensors->getPressure(false);
        if(initialTopValue<newTopValue) terminal->writeString("ITL");
        else if(initialBottomValue<newBottomValue) terminal->writeString("IBL");
        else if(initialTopValue>newTopValue) terminal->writeString("ITM");
        else if(initialBottomValue>newBottomValue) terminal->writeString("IBM");
        else terminal->writeString("OK");
        delay(500);
        in = terminal->readLine();
      }
    }
  }
}

void loopSecondCore(void *pvParameters){
  // while(true) {
  //   terminal->readLine();
  //   delay(1000);
  // }
  // vTaskDelay(10);
}

void printMsg(const char *msg){
  Serial.print(xPortGetCoreID());
  Serial.print(".");
  Serial.print(xPortGetTickRateHz());
  Serial.print(":");
  Serial.println(msg);
}
