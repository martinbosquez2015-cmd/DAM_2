// Smart Room Voice Control

const habitacion = document.getElementById('habitacion');
const persiana = document.getElementById('persiana');
const temperatura = document.getElementById('temperatura');
const estadoVoz = document.getElementById('estadoVoz');
const textoReconocido = document.getElementById('textoReconocido');
const ultimaAccion = document.getElementById('ultimaAccion');
const entradaManual = document.getElementById('entradaManual');

let temp = 22;
let luzEncendida = false;
let persianaAbierta = true;
let modoNoche = false;

function normalizar(texto) {
  return texto.toLowerCase().trim()
    .normalize('NFD').replace(/[\u0300-\u036f]/g, '');
}

function informar(mensaje, error = false) {
  ultimaAccion.textContent = mensaje;
  ultimaAccion.classList.toggle('error', error);
}

function encenderLuz() { 
  if(luzEncendida == true){
    informar("La luz ya está encendida");
  }
  else{
    luzEncendida = true; 
    habitacion.classList.add('light-on'); 
    informar('Luz encendida'); 
  }

}
function apagarLuz() { 
    if(luzEncendida == false){
    informar("No ves que no hay luz?!");
  }
  else{
  luzEncendida = false; 
  habitacion.classList.remove('light-on'); 
  informar('Luz apagada'); 
  }
}
function subirTemperatura() { 
  temp++; 
  temperatura.textContent = temp; 
  informar(`Temperatura: ${temp} °C`); 
}
function bajarTemperatura() { 
  temp--; 
  temperatura.textContent = temp; 
  informar(`Temperatura: ${temp} °C`); 
}


// Me da pereza aher muchas funciones, para hacer solo una le pedi ayuda a Gepetto jsjs
function ponerNuevaTemperatura(nuevaTemp){
  if(nuevaTemp<16 || nuevaTemp> 30){
    informar(`Mano, quiero estar tranquilo, no te pases con mis limites de temperaturas\nMi limite es entre 16 y 30 ºC tu lo quieres poner en ${nuevaTemp}ºC`)
  }
  else{
    temp= nuevaTemp; 
    temperatura.textContent = temp; 
    informar(`Temperatura: ${temp} °C`); 
  }

}




function abrirPersiana() { 
  if(persianaAbierta == true){
    informar("Ya no le puedo abrir más!");
  }
  else{
  persianaAbierta = true; 
  persiana.classList.remove('closed'); 
  persiana.classList.add('open'); 
  informar('Persiana abierta'); 
  }

}
function cerrarPersiana() { 
    if(persianaAbierta == false){
    informar("La persiana ya está cerradisima");
  }
  else{
  persianaAbierta = false; 
  persiana.classList.remove('open'); 
  persiana.classList.add('closed'); 
  informar('Persiana cerrada'); 
  }

}
function activarModoNoche() { 
  modoNoche = true; 
  habitacion.classList.add('night'); 
  cerrarPersiana(); 
  informar('Modo noche activado'); 
  apagarLuz();
  ponerNuevaTemperatura(20);

}
function activarModoVerano(){
  modoVerano = true; 
  habitacion.classList.add('light-on'); 
  cerrarPersiana(); 
  informar('Modo verano activado'); 
  apagarLuz();
  ponerNuevaTemperatura(16);
}

function activarModoSexo(){

}

function reiniciarHab(){
  temp = 22;
  luzEncendida = false;
  persianaAbierta = true;
  modoNoche = false;
}

function ejecutarComando(textoOriginal) {
  const texto = normalizar(textoOriginal);
  textoReconocido.textContent = textoOriginal;
  
  // ver si hay comando siquiera
  if(texto === ""){
            informar('Introduce un comando', true);
        return;
    }
  
  
  ///Reiniciar
  else if(texto.includes("reiniciar"))
    reiniciarHab()


  //modos
  else if (texto.includes("modo")){
    if(texto.includes("noche"))
      activarModoNoche()
    else if(texto.includes("verano"))
      activarModoVerano()
    else if(texto.includes("sexo"))
      activarModoSexo()
  }
  
  // para referirnos a cosas de la luz
  else if(texto.includes("luz")){
    
    if(entraEnAlguna(texto, palabrasEncender))
      encenderLuz();
    else if (entraEnAlguna(texto, palabrasApagar))
      apagarLuz();

  }

  // para temperaturas
  else if(texto.includes("temperatura")){
      // esta manera me la recomienda Geppeto, es muy eficaz, mi idea era crear un diccionario con numeros del 1 al 100 realcionando el texto con su entero
    const coincidencia = texto.match(/\d+/);
    const numero = coincidencia
    ? Number(coincidencia[0])
    : null;



        if(entraEnAlguna(texto, palabrasSubir))
      ponerNuevaTemperatura(  temp+numero)
    else if (entraEnAlguna(texto, palabrasbajar))
      ponerNuevaTemperatura(  temp- numero)
    else if (entraEnAlguna(texto, palabrasPoner))
      ponerNuevaTemperatura(  numero)
  }


  //PAra persiana
  else if (texto.includes("persiana")){
    if(entraEnAlguna(texto, palabrasAbrir))
      abrirPersiana;
    else if(texto.includes("persiana"))
      cerrarPersiana;
  }



  // Usa una estructura if / else if
 
}


//funcion para matchear palabras con texto
  function entraEnAlguna(texto, palabras){
    //funcion .some() nos busca una coincidencia
    const coincide = palabras.some(palabra => texto.includes(palabra));//aquí también me ayudó mi amigo Geppeto 
    return coincide;

  }

// --- Reconocimiento de voz: no necesitas modificar esta parte ---
const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
let recognition = null;


if (SpeechRecognition) {
  recognition = new SpeechRecognition();
  recognition.lang = 'es-ES';
  recognition.continuous = true;
  recognition.interimResults = false;

  recognition.onstart = () => {
    estadoVoz.textContent = 'Escuchando';
    estadoVoz.className = 'badge listening';
  };

  recognition.onend = () => {
    estadoVoz.textContent = 'Detenido';
    estadoVoz.className = 'badge stopped';
  };

  recognition.onresult = (event) => {
    const ultimo = event.results[event.results.length - 1][0].transcript;
    ejecutarComando(ultimo);
  };

  recognition.onerror = (event) => informar(`Error de voz: ${event.error}`, true);
} else {
  informar('Este navegador no ofrece reconocimiento de voz. Usa la prueba manual.', true);
}

document.getElementById('btnIniciar').addEventListener('click', () => {
  if (recognition) recognition.start();
});

document.getElementById('btnDetener').addEventListener('click', () => {
  if (recognition) recognition.stop();
});

document.getElementById('btnProbar').addEventListener('click', () => {
  ejecutarComando(entradaManual.value);
});

entradaManual.addEventListener('keydown', (e) => {
  if (e.key === 'Enter') ejecutarComando(entradaManual.value);
});



const palabrasEncender = [
  "encender",
  "prender",
  "enciende",
  "prende"
];

const palabrasApagar = [
    "apagar",
    "apaga"
];

const palabrasSubir = [
    "sube",
    "subir",
    "subele"
];
const palabrasbajar = [
    "bajar",
    "baja",
    "bajale"
];

const palabrasPoner = [

];

const palabrasAbrir = [
    "bajar",
    "baja",
    "bajale"
];

const palabrasCerrar = [
    "bajar",
    "baja",
    "bajale"
];