#include "../../include/sensors/Bumper.h"
#include "Arduino.h"


bool Bumper::isClosed(){
    return this->readValues()==LOW;
}