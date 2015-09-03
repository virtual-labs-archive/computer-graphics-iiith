@echo off
 
Set RegQry=HKLM\Hardware\Description\System\CentralProcessor\0
 
REG.exe Query %RegQry% > checkOS.txt
 
Find /i "x86" < CheckOS.txt > StringCheck.txt
 
IF %ERRORLEVEL% == 0 (
	Echo "Detected Microsoft Windows 32 Bit Operating system"
	java -cp win32\gluegen-rt.jar;win32\jogl.all.jar;win32\nativewindow.all.jar;win32\newt.all.jar;.;.. -D"java.library.path=win32" experiments.experiment%1.Experiment%1
) ELSE (
	Echo "Detected Microsoft Windows 64 Bit Operating System"
	java -cp win64\gluegen-rt.jar;win64\jogl.all.jar;win64\nativewindow.all.jar;win64\newt.all.jar;.;.. -D"java.library.path=win64" experiments.experiment%1.Experiment%1
)
