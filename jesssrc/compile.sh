#!/bin/bash

PATH_TO_FX="./javafx-sdk-16/lib"

javac --module-path $PATH_TO_FX --add-modules=javafx.controls,javafx.graphics,javafx.fxml,javafx.base *.java

java --module-path $PATH_TO_FX --add-modules=javafx.controls,javafx.graphics,javafx.fxml,javafx.base Game


