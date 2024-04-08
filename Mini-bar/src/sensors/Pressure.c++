#include "../../include/sensors/Pressures.h"


int Pressure::getPressure(){
    return this->readValues();
}
