/**
 * 
 */

/* Cando o usuario clicka o botón, debe cambiar entre esconder 
            e amosar os contidos (menudes-content) */
function desplegarMenu() {
    document.getElementById("menudes").classList.toggle("show");
}

/* MODAL */
var modal = $(".modal")[0];
var btn = $(".myBtn")[0];
var span = $(".close")[0];

var modal1 = $(".modal")[1];
var btn1 = $(".myBtn")[1];
var span1 = $(".close")[1];
var button = $(".aceptar")[0];

btn.onclick = function(e) {
  e.preventDefault();
  $(modal).addClass('sta-display');
}

span.onclick = function() {
  $(modal).removeClass('sta-display');
}

button.onclick = function() {
  $(modal).removeClass('sta-display');
}

window.onclick = function(event) {
  if (event.target == modal) {
    $(modal).removeClass('sta-display');
  }
}

btn1.onclick = function(e) {
  e.preventDefault();
  $(modal1).addClass('sta-display');
}

span1.onclick = function() {
  $(modal1).removeClass('sta-display');
}

window.onclick = function(event) {
  if (event.target == modal) {
    $(modal1).removeClass('sta-display');
  }
}            
            

          

           