
// Variables globales
let currentSlideIndex = 0;
let carouselAutoPlay = null;

// Función para mostrar un slide específico
function showSlide(index) {
    const slides = document.querySelectorAll('.carousel-slide');
    const indicators = document.querySelectorAll('.carousel-indicator');

    if (slides.length === 0) return;

    // Ocultar todos los slides
    slides.forEach(slide => {
        slide.style.display = 'none';
        slide.classList.remove('active');
    });

    // Remover active de todos los indicadores
    indicators.forEach(indicator => {
        indicator.classList.remove('active');
    });

    // Validar índice
    if (index >= slides.length) {
        currentSlideIndex = 0;
    } else if (index < 0) {
        currentSlideIndex = slides.length - 1;
    } else {
        currentSlideIndex = index;
    }

    // Mostrar slide actual
    if (slides[currentSlideIndex]) {
        slides[currentSlideIndex].style.display = 'flex';
        slides[currentSlideIndex].classList.add('active');
    }

    // Activar indicador correspondiente
    if (indicators[currentSlideIndex]) {
        indicators[currentSlideIndex].classList.add('active');
    }
}

// Función para mover slides
function moveSlide(direction) {
    showSlide(currentSlideIndex + direction);
}

// Función para ir a un slide específico
function currentSlide(index) {
    showSlide(index);
}

// Función para iniciar autoplay
function startAutoPlay() {
    if (carouselAutoPlay) {
        clearInterval(carouselAutoPlay);
    }

    carouselAutoPlay = setInterval(() => {
        moveSlide(1);
    }, 4000);


}

// Función para pausar autoplay
function pauseAutoPlay() {
    if (carouselAutoPlay) {
        clearInterval(carouselAutoPlay);
        carouselAutoPlay = null;
    }

}

// Función para inicializar el carrusel
function initCarousel() {


    const slides = document.querySelectorAll('.carousel-slide');


    if (slides.length === 0) {

        return;
    }

    // Mostrar el primer slide
    showSlide(0);

    // Configurar eventos de hover
    const container = document.querySelector('.carousel-container');
    if (container) {
        container.addEventListener('mouseenter', pauseAutoPlay);
        container.addEventListener('mouseleave', startAutoPlay);
    }

    // Iniciar autoplay
    startAutoPlay();


}

// Inicializar cuando el DOM esté listo
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initCarousel);
} else {
    initCarousel();
}
