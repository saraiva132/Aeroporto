#!/bin/bash
#!/usr/bin/expect

exec=(ZonaDesembarqueMain RecolhaBagagemMain PoraoMain TransferenciaTerminalMain AutocarroMain null TransicaoAeroportoMain LoggingMain BagageiroMain MotoristaMain PassageiroMain)

jarfile=(ZonaDesembarque recolhaBagagem Porao TransferenciaTerminal Autocarro null TransicaoAeroporto Logging Bagageiro Motorista Passageiro)

for i in 1 2 3 4 5 7 8 9 10 11
do


printf "Copiando aplicação para a workstation: $i\n"
printf "...\n"

/usr/bin/expect<<EOF
set timeout 5
spawn /usr/bin/scp -o ConnectTimeout=2 -r ./SDAeroporto-Trabalho2.jar sd0206@l040101-ws$i.clients.ua.pt:~/

while {1} {
  expect {
  
    eof                          {break}
    "The authenticity of host"   {send -- "yes\r"}
    "*?assword:"                 {send -- "fraderafa\r"
				 send "\r"}
    "*\]"                        {send "exit\r"}
  }
  }
EOF
done

for i in 1 2 3 4 5 7 8 9 10 11
do

set timeout 2
printf "Copiando configuração para a workstation: $i\n"
printf "...\n"

/usr/bin/expect<<EOF
set timeout 5
spawn /usr/bin/scp -o ConnectTimeout=2 -r ./conf.xml sd0206@l040101-ws$i.clients.ua.pt:~/

while {1} {
  expect {
  
    eof                          {break}
    "The authenticity of host"   {send -- "yes\r"}
    "*?assword:"                 {send -- "fraderafa\r"
				 send "\r"}
    "*\]"                        {send "exit\r"}
  }
  }
EOF
done


for i in 1 2 3 4 5 7 8 9 10 11
do

printf "Correndo a aplicação na workstation: $i\n"
printf "...\n"
/usr/bin/expect<<EOF
set timeout 5
spawn /usr/bin/ssh -o ConnectTimeout=3 sd0206@l040101-ws$i.clients.ua.pt
expect "*?assword:*"
send -- "fraderafa\r"
send -- "\r"
expect "*$ "
send -- "mv SDAeroporto-Trabalho2.jar ${jarfile[$i-1]}.jar"
send -- "\r"
expect "*$ "
send -- "java -cp ${jarfile[$i-1]}.jar sdaeroporto.${exec[$i-1]}&"
send -- "\r"
send -- "\r"
expect "*$ "
send -- "exit\r"
expect eof
wait
EOF
done

