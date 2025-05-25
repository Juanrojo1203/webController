/**
 * Carrusel simple y eficiente
 * @version 3.0 - Optimizado - SIN PETICIONES POST
 */

console.log('🚀 Carrusel.js v3.0 cargado correctamente - SIN peticiones POST');

class Carrusel {
    constructor() {
        this.currentSlide = 0;
        this.totalSlides = 0;
        this.autoPlayInterval = null;
        this.isPlaying = true;

        this.init();
    }

    init() {
        const track = document.getElementById('carouselTrack');

        if (!track) {
            console.error('No se encontró el elemento carouselTrack');
            return;
        }

        this.totalSlides = track.children.length;

        if (this.totalSlides === 0) {
            console.error('No hay slides en el carrusel');
            return;
        }

        // Ajustar el ancho del track dinámicamente
        const trackWidth = this.totalSlides * 100; // 100% por cada slide
        track.style.width = `${trackWidth}%`;

        // Ajustar el ancho de cada slide
        const slideWidth = 100 / this.totalSlides;
        const slides = track.children;
        for (let i = 0; i < slides.length; i++) {
            slides[i].style.width = `${slideWidth}%`;
        }

        this.setupEvents();
        this.showSlide(0);
        this.startAutoPlay();
    }

    setupEvents() {
        const container = document.querySelector('.carousel-container');
        if (container) {
            container.addEventListener('mouseenter', () => this.pauseAutoPlay());
            container.addEventListener('mouseleave', () => this.resumeAutoPlay());
        }

        // Funciones globales para HTML
        window.moveSlide = (direction) => this.move(direction);
        // Removemos window.currentSlide porque ya está definida globalmente
    }

    showSlide(index) {
        const track = document.getElementById('carouselTrack');
        if (!track) return;

        // Validar índice
        if (index >= this.totalSlides) this.currentSlide = 0;
        else if (index < 0) this.currentSlide = this.totalSlides - 1;
        else this.currentSlide = index;

        // Calcular el porcentaje de movimiento basado en el número de slides
        const slideWidth = 100 / this.totalSlides; // Cada slide ocupa este % del ancho total
        const translateX = -this.currentSlide * slideWidth;

        track.style.transform = `translateX(${translateX}%)`;

        // Actualizar indicadores
        this.updateIndicators();
    }

    updateIndicators() {
        const indicators = document.querySelectorAll('.carousel-indicator');

        // Debug temporal
        console.log(`Indicadores encontrados: ${indicators.length}, Slide actual: ${this.currentSlide}`);

        // Remover la clase 'active' de todos los indicadores
        indicators.forEach(indicator => {
            indicator.classList.remove('active');
        });

        // Agregar la clase 'active' al indicador actual
        if (indicators[this.currentSlide]) {
            indicators[this.currentSlide].classList.add('active');
            console.log(`Indicador ${this.currentSlide} activado - clases:`, indicators[this.currentSlide].className);
            console.log(`Indicador ${this.currentSlide} estilo:`, window.getComputedStyle(indicators[this.currentSlide]).backgroundColor);
        }
    }

    move(direction) {
        this.showSlide(this.currentSlide + direction);
    }

    goTo(index) {
        this.showSlide(index);
    }

    startAutoPlay() {
        this.autoPlayInterval = setInterval(() => {
            if (this.isPlaying) this.move(1);
        }, 3000);
    }

    pauseAutoPlay() {
        this.isPlaying = false;
    }

    resumeAutoPlay() {
        this.isPlaying = true;
    }
}

// Función global para los indicadores
function currentSlide(n) {
    if (window.carousel) {
        console.log(`currentSlide llamado con: ${n}`);
        window.carousel.showSlide(n); // Usamos índices base-0
    }
}

// Inicializar
document.addEventListener('DOMContentLoaded', () => {
    window.carousel = new Carrusel();
});
