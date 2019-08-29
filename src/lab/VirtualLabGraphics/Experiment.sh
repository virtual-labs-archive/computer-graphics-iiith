#!/bin/bash
os=`uname -s | grep Darwin`
bitval=`uname -m | grep 64`
if [ $# -ne 1 ]
then
	echo "Usage : $0 <experiment no. 1/2/3/4/5>"
elif [ $os ]
then
	echo "$0 : Detected Mac OS X"
	java -cp "macosx/gluegen-rt.jar:macosx/jogl.all.jar:macosx/nativewindow.all.jar:macosx/newt.all.jar:.:.." -Djava.library.path=macosx experiments.experiment$1.Experiment$1
elif [ $bitval ]
then
	echo "$0 : Detected 64 bit Linux"
	java -cp "lib64/gluegen-rt.jar:lib64/jogl.all.jar:lib64/nativewindow.all.jar:lib64/newt.all.jar:.:.." -Djava.library.path=lib64 experiments.experiment$1.Experiment$1
else
	echo "$0 : Detected 32 bit Linux"
	java -cp "lib32/gluegen-rt.jar:lib32/jogl.all.jar:lib32/nativewindow.all.jar:lib32/newt.all.jar:.:.." -Djava.library.path=lib32 experiments.experiment$1.Experiment$1
fi
