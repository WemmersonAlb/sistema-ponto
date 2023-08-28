setInterval(()=>{
   var hora = new Date()
   var h = hora.getHours()
   var m = hora.getMinutes()
   var s = hora.getSeconds()
   var dia = hora.getDate()
   var mes = hora.getMonth()
   var ano = hora.getYear()
   var semana = hora.getDay()
   var DiaDaSemana = document.getElementById('DiaDaSemana')
   switch(semana){
      case 0:
         DiaDaSemana.innerHTML="Domingo"
         break
      case 1:
         DiaDaSemana.innerHTML="Segunda-Feira"
         break
      case 2:
         DiaDaSemana.innerHTML="Terça-Feira"
         break
      case 3:
         DiaDaSemana.innerHTML="Quarta-Feira"
         break
      case 4:
         DiaDaSemana.innerHTML="Quinta-Feira"
         break
      case 5:
         DiaDaSemana.innerHTML="Sexta-Feira"
         break
      case 6:
         DiaDaSemana.innerHTML="Sábado"
         break
   }
   var data = document.getElementById('data')
   switch (mes){
      case 0:
         data.innerHTML=`${dia} de Janeiro de ${ano+1900}`
         break
      case 1:
         data.innerHTML=`${dia} de Fevereiro de ${ano+1900}`
         break
      case 2:
         data.innerHTML=`${dia} de Março de ${ano+1900}`
         break
      case 3:
         data.innerHTML=`${dia} de Abril de ${ano+1900}`
         break
      case 4:
         data.innerHTML=`${dia} de Maio de ${ano+1900}`
         break
      case 5:
         data.innerHTML=`${dia} de Junho de ${ano+1900}`
         break
      case 6:
         data.innerHTML=`${dia} de Julho de ${ano+1900}`
         break
      case 7:
         data.innerHTML=`${dia} de Agosto de ${ano+1900}`
         break
      case 8:
         data.innerHTML=`${dia} de Setembro de ${ano+1900}`
         break
      case 9:
         data.innerHTML=`${dia} de Outubro de ${ano+1900}`
         break
      case 10:
         data.innerHTML=`${dia} de Novembro de ${ano+1900}`
         break
      case 11:
         data.innerHTML=`${dia} de Dezembro de ${ano+1900}`
         break
   }
   var relogio = document.getElementById('relogio')
   if(h>9&&m>9&&s>9){
      relogio.innerHTML=`${h} : ${m} : ${s}`
   }else if(h>9&&m>9){
      relogio.innerHTML=`${h} : ${m} : ${0}${s}`
   }else if(h>9){
      relogio.innerHTML=`${h} : ${0}${m} : ${0}${s}`
   }else if(m>9&&s>9){
      relogio.innerHTML=`${0}${h} : ${m} : ${s}`
   }else if(s>9){
      relogio.innerHTML=`${0}${h} : ${0}${m} : ${s}`
   }else if(m>9){
      relogio.innerHTML=`${0}${h} : ${m} : ${0}${s}`
   }
}, 1000)