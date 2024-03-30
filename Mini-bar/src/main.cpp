#include <Arduino.h>
#include <structures/Sensors.h>
#include <structures/BluetoothTerminal.h>

void loopSecondCore(void *pvParameters);
void printMsg(const char *msg);

#define DHT_TOP 2
#define DHT_DOOR 3
#define DHT_BOTTOM 4
#define BUMPER 5
#define PRESSURE_TOP 6
#define PRESSURE_BOTTOM 7

Sensors* sensors;
BluetoothTerminal* terminal;
TaskHandle_t* taskHandle;

void setup() {
  //Inicializacion monitor serial:
  Serial.begin(115200);
  
  //Creación de los objetos base:
  sensors = new Sensors(DHT_TOP,DHT_BOTTOM,DHT_DOOR,BUMPER,PRESSURE_TOP,PRESSURE_BOTTOM);
  terminal = new BluetoothTerminal("ESP32-BT-MINIBAR");

	//Creacion tarea segundo hilo:
  xTaskCreatePinnedToCore(loopSecondCore,"SecondaryCore",1000,NULL,1,taskHandle,0);
}

void loop() {
  printMsg("HOLA");
  if (Serial.available()) {
    terminal->writeChar(Serial.read());
  }
  terminal->readLine();
  delay(400);
}

void loopSecondCore(void *pvParameters){
  while(true) {
    printMsg("HOLA");
    delay(400);
  }
  vTaskDelay(10);
}

void printMsg(const char *msg){
  Serial.print(xPortGetCoreID());
  Serial.print(".");
  Serial.print(xPortGetTickRateHz());
  Serial.print(":");
  Serial.println(msg);
}
