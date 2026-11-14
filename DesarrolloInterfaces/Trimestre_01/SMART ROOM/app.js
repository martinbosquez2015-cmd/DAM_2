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
let modoVerano = false;
let modoSexo = false;

function normalizar(texto) {
  return texto.toLowerCase().trim()
    .normalize('NFD').replace(/[\u0300-\u036f]/g, '');
}

function informar(mensaje, error = false) {
  ultimaAccion.textContent = mensaje;
  ultimaAccion.classList.toggle('error', error);
}

function encenderLuz() {
  if (luzEncendida == true) {
    informar("La luz ya está encendida");
  }
  else {
    luzEncendida = true;
    habitacion.classList.add('light-on');
    informar('Luz encendida');
  }

}
function apagarLuz() {
  if (luzEncendida == false) {
    informar("No ves que no hay luz?!");
  }
  else {
    luzEncendida = false;
    habitacion.classList.remove('light-on');
    informar('Luz apagada');
  }
}
function subirTemperatura() {
  ponerNuevaTemperatura(temp + 1)
}
function bajarTemperatura() {
  ponerNuevaTemperatura(temp - 1)
}


// Me da pereza aher muchas funciones, para hacer solo una le pedi ayuda a Gepetto jsjs
function ponerNuevaTemperatura(nuevaTemp) {
  if (nuevaTemp < 16 || nuevaTemp > 30) {
    informar(`Mano, quiero estar tranquilo, no te pases con mis limites de temperaturas\nMi limite es entre 16 y 30 ºC tu lo quieres poner en ${nuevaTemp}ºC`)
  }
  else {
    temp = nuevaTemp;
    temperatura.textContent = temp;
    informar(`Temperatura: ${temp} °C`);
  }

}




function abrirPersiana() {
  if (persianaAbierta == true) {
    informar("Ya no le puedo abrir más!");
  }
  else {
    persianaAbierta = true;
    persiana.classList.remove('closed');
    persiana.classList.add('open');
    informar('Persiana abierta');
  }

}
function cerrarPersiana() {
  if (persianaAbierta == false) {
    informar("La persiana ya está cerradisima");
  }
  else {
    persianaAbierta = false;
    persiana.classList.remove('open');
    persiana.classList.add('closed');
    informar('Persiana cerrada');
  }

}
function activarModoNoche() {
  if (modoNoche) {
    informar("El modo noche ya está activado");
    return;
  }

  modoNoche = true;
  modoSexo = false;
  modoVerano = false;

  habitacion.classList.remove('summer', 'seso');
  habitacion.classList.add('night');

  apagarLuz();
  cerrarPersiana();
  ponerNuevaTemperatura(20);

  informar("Modo noche activado");


}
function activarModoVerano() {
  if (modoVerano == true) {
    informar("El modo verano ya está activado")
  }
  else {
    modoNoche = false;
    modoSexo = false;
    modoVerano = true;
    habitacion.classList.remove('night', 'seso');
    habitacion.classList.add('summer');
    encenderLuz();
    cerrarPersiana();
    ponerNuevaTemperatura(18);


    informar('Modo verano activado');

  }
}

function activarModoSexo() {
  if (modoSexo == true) {
    informar("El modo seso ya está activado")
  }
  else {
    modoNoche = false;
    modoSexo = true;
    modoVerano = false;
    habitacion.classList.remove('night', 'verano');
    habitacion.classList.add('seso');
    cerrarPersiana();
    apagarLuz();
    ponerNuevaTemperatura(24);
    informar('Modo activado 7u7');

  }
}

function reiniciarHab() {
  modoNoche = false;
  modoVerano = false;
  modoSexo = false;
  apagarLuz();
  abrirPersiana();
  ponerNuevaTemperatura(22);

  habitacion.classList.remove(
    'night',
    'summer',
    'seso',
    'light-on',
  )

}

function easterEggs(texto) {

  if (texto.includes("gracias")) {
    informar("¡De nada, mi pana! Para eso estamos papi");
    return true;
  }

  if (
    texto.includes("que bonita habitacion") ||
    texto.includes("que guapa habitacion")
  ) {
    informar("Lo sé, tengo un decorador de cinco estrellas");
    return true;
  }

  if (texto === "por favor" || texto === "porfa") {
    informar("¡Pero claro, jefe! No hace falta tanta formalidad");
    return true;
  }

  if (
    texto.includes("tengo calor") ||
    texto.includes("me estoy asando")
  ) {
    ponerNuevaTemperatura(temp - 1)
    informar("¡Uf, mano! Te he bajado un gradito");
    return true;
  }

  if (
    texto.includes("tengo frio") ||
    texto.includes("me estoy congelando")
  ) {
    ponerNuevaTemperatura(temp + 1)
    informar("¡Abrígate, hermano! Te he subido un gradito")
    return true;
  }

  return false;

}

function ejecutarComando(textoOriginal) {
  const texto = normalizar(textoOriginal);
  textoReconocido.textContent = textoOriginal;

  // ver si hay comando siquiera
  if (texto === "") {
    informar('Introduce un comando', true);
    return;
  }

  if (easterEggs(texto)) {
    return;
  }
  ///Reiniciar
  else if (texto.includes("reiniciar")) {
    reiniciarHab()
  }

  //modos
  else if (texto.includes("modo")) {
    if (texto.includes("noche"))
      activarModoNoche()
    else if (texto.includes("verano"))
      activarModoVerano()
    else if (texto.includes("sexo"))
      activarModoSexo()
    else
      informar("No conozco ese modo mi brother", true)
  }

  // para referirnos a cosas de la luz
  else if (texto.includes("luz")) {

    if (entraEnAlguna(texto, palabrasEncender))
      encenderLuz();
    else if (entraEnAlguna(texto, palabrasApagar))
      apagarLuz();

  }

  // para temperaturas
  else if (texto.includes("temperatura")) {
    // esta manera me la recomienda Geppeto, es muy eficaz, mi idea era crear un diccionario con numeros del 1 al 100 realcionando el texto con su entero
    const coincidencia = texto.match(/\d+/);
    const numero = coincidencia
      ? Number(coincidencia[0])
      : 1;



    if (entraEnAlguna(texto, palabrasSubir))
      ponerNuevaTemperatura(temp + numero)
    else if (entraEnAlguna(texto, palabrasBajar))
      ponerNuevaTemperatura(temp - numero)
    else if (entraEnAlguna(texto, palabrasPoner)) {
      if (coincidencia) {
        ponerNuevaTemperatura(numero);
      } else {
        informar("¿A cuántos grados quieres ponerla?", true);
      }
    }

    else
      informar("Me falta informacion sobre la temperatura", true)
  }


  //PAra persiana
  else if (texto.includes("persiana")) {
    if (entraEnAlguna(texto, palabrasAbrir))
      abrirPersiana();
    else if (entraEnAlguna(texto, palabrasCerrar))
      cerrarPersiana();
  }

  else
    informar("Comando no reconocido", true)

  // Usa una estructura if / else if

}


//funcion para matchear palabras con texto
function entraEnAlguna(texto, palabras) {
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
  "enciende",
  "encenderme",
  "prender",
  "prende",
  "activa",
  "activar",
  "pon",

  "dale",
  "enciéndeme",
  "mete",

  "prendela",
  "pon",
  "echale",

  "prende esa vaina",
  "activa esa vaina",
  "metele",

  "prende",
  "dale",
  "ponle"
];

const palabrasApagar = [
  "apagar",
  "apaga",
  "desactivar",
  "desactiva",
  "desconectar",
  "desconecta",

  "apagame",
  "quita",

  "apagala",
  "bajale",

  "apaga esa vaina",


  "apaga",
  "corta",


  "apagalo",
  "fuera"
];

const palabrasSubir = [
  "sube",
  "subir",
  "súbele",
  "aumenta",
  "aumentar",
  "incrementa",
  "incrementar",
  "eleva",
  "elevar",

  "dale caña",
  "mete más calor",


  "subele más",
  "echale más",

  "subila",
  "aumentala",

  "matele mas",
  "ponla más alta"
];

const palabrasBajar = [
  "bajar",
  "baja",
  "bájale",
  "disminuye",
  "disminuir",
  "reduce",
  "reducir",
  "desciende",
  "descender",

  "afloja",
  "quita calor",


  "bajale",
  "echale menos",

  "bajala",
  "reducila",


  "suaviza",
  "ponla más baja"
];

const palabrasPoner = [
  "poner",
  "pon",
  "ponme",
  "establecer",
  "establece",
  "ajustar",
  "ajusta",
  "configurar",
  "configura",
  "fijar",
  "fija",
  "dejar",
  "dejala",
  "colocar",
  "coloca",

  "dejame",
  "ponla",
  "deja",
  "este",
  "marca",
  "clava"
];

const palabrasAbrir = [
  "abrir",
  "abre",
  "abrela",
  "abrime",
  "desplegar",
  "despliega",


  "subela",
  "recoge",
  "levanta",

  "abrela bien",
  "destapa",


  "abri",
  "levantá",

  "destapala",
  "subeme",

];

const palabrasCerrar = [
  "cerrar",
  "cierra",
  "cierrala",
  "cierrala",
  "bajar",
  "baja",
  "bajala",

  "echa",


  "tapala",
  "achala abajo",

  "cerra",
  "baja",

  "tapame eso",
  "cubrela",


  "cierrame"
];
