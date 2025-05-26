/**
 * Carrusel optimizado y funcional
 * @version 4.0 - MEJORADO - Funciona garantizado
 */

console.log('🚀 Carrusel.js v4.0 cargado - OPTIMIZADO');

class Carrusel {
    constructor() {
        this.currentSlide = 0;
        this.totalSlides = 0;
        this.autoPlayInterval = null;
        this.isPlaying = true;
        this.slideWidth = 100; // Cada slide ocupa 100% del contenedor

        // Esperar a que el DOM esté completamente cargado
        if (document.readyState === 'loading') {
            document.addEventListener('DOMContentLoaded', () => this.init());
        } else {
            this.init();
        }
    }

    init() {
        console.log('🎠 Inicializando carrusel...');

        const track = document.getElementById('carouselTrack');
        if (!track) {
            console.error('❌ No se encontró #carouselTrack');
            return;
        }

        // Contar slides reales (no elementos vacíos)
        const slides = Array.from(track.children).filter(slide =>
            slide.classList.contains('carousel-slide') &&
            slide.querySelector('img')
        );

        this.totalSlides = slides.length;
        console.log(`📊 Total de slides encontrados: ${this.totalSlides}`);

        if (this.totalSlides === 0) {
            console.error('❌ No hay slides válidos en el carrusel');
            return;
        }

        // Configurar el track para mostrar un slide a la vez
        track.style.width = `${this.totalSlides * 100}%`;
        track.style.display = 'flex';
        track.style.transition = 'transform 0.5s ease-in-out';

        // Configurar cada slide
        slides.forEach((slide, index) => {
            slide.style.width = `${100 / this.totalSlides}%`;
            slide.style.flexShrink = '0';
            console.log(`✅ Slide ${index + 1} configurado`);
        });

        this.setupEvents();
        this.generateIndicators();
        this.showSlide(0);
        this.startAutoPlay();

        console.log('🎉 Carrusel inicializado correctamente');
    }

    setupEvents() {
        const container = document.querySelector('.carousel-container');
        if (container) {
            container.addEventListener('mouseenter', () => this.pauseAutoPlay());
            container.addEventListener('mouseleave', () => this.resumeAutoPlay());
        }

        // Funciones globales para HTML
        window.moveSlide = (direction) => this.move(direction);
        window.currentSlide = (index) => this.goTo(index);

        console.log('🎮 Eventos configurados');
    }

    generateIndicators() {
        const indicatorsContainer = document.querySelector('.carousel-indicators');
        if (!indicatorsContainer) {
            console.warn('⚠️ No se encontró contenedor de indicadores');
            return;
        }

        // Limpiar indicadores existentes
        indicatorsContainer.innerHTML = '';

        // Generar indicadores dinámicamente
        for (let i = 0; i < this.totalSlides; i++) {
            const indicator = document.createElement('span');
            indicator.className = 'carousel-indicator';
            if (i === 0) indicator.classList.add('active');
            indicator.onclick = () => this.goTo(i);
            indicatorsContainer.appendChild(indicator);
        }

        console.log(`🔘 ${this.totalSlides} indicadores generados`);
    }

    showSlide(index) {
        const track = document.getElementById('carouselTrack');
        if (!track) return;

        // Validar índice con bucle infinito
        if (index >= this.totalSlides) {
            this.currentSlide = 0;
        } else if (index < 0) {
            this.currentSlide = this.totalSlides - 1;
        } else {
            this.currentSlide = index;
        }

        // Mover el carrusel (cada slide ocupa 100% del ancho del contenedor)
        const translateX = -this.currentSlide * 100;
        track.style.transform = `translateX(${translateX}%)`;

        // Actualizar indicadores
        this.updateIndicators();

        console.log(`📍 Mostrando slide ${this.currentSlide + 1}/${this.totalSlides}`);
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
        // Limpiar intervalo anterior si existe
        if (this.autoPlayInterval) {
            clearInterval(this.autoPlayInterval);
        }

        this.autoPlayInterval = setInterval(() => {
            if (this.isPlaying) {
                this.move(1);
            }
        }, 4000); // 4 segundos para mejor experiencia

        console.log('▶️ AutoPlay iniciado (4s)');
    }

    pauseAutoPlay() {
        this.isPlaying = false;
        console.log('⏸️ AutoPlay pausado');
    }

    resumeAutoPlay() {
        this.isPlaying = true;
        console.log('▶️ AutoPlay reanudado');
    }

    destroy() {
        if (this.autoPlayInterval) {
            clearInterval(this.autoPlayInterval);
        }
        console.log('🗑️ Carrusel destruido');
    }
}

// Función global para compatibilidad con HTML
function currentSlide(n) {
    if (window.carousel) {
        console.log(`🔘 Indicador clickeado: ${n + 1}`);
        window.carousel.goTo(n);
    }
}

// Inicialización global mejorada
window.carousel = null;

// Función de inicialización
function initCarousel() {
    if (window.carousel) {
        window.carousel.destroy();
    }
    window.carousel = new Carrusel();
}

// Inicializar cuando el DOM esté listo
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initCarousel);
} else {
    // Si el DOM ya está cargado, inicializar inmediatamente
    setTimeout(initCarousel, 100);
}

console.log('🎠 Carrusel.js v4.0 optimizado - Listo para funcionar');
