const button = document.querySelector('#addTask');
const form = document.querySelector('.myForm');
const popup = document.querySelector('.popup');
const cancel = document.querySelector('.cancel')

button.addEventListener('click', () => {
    form.classList.add('open');
    popup.classList.add('popup_open');
});

cancel.addEventListener('click', () =>{
    form.classList.remove('open');
    popup.classList.remove('popup_open');
});
