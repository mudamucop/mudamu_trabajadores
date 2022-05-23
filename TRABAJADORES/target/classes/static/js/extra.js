document.addEventListener('DOMContentLoaded', (event) => {
    let originalSize = null;

    function handleMouseOver(e) {
        this.style.opacity = 0.5;
        originalSize = this.style.fontSize;
        this.style.fontSize = '1em';
    }

    function handleMouseOut(e) {
        this.style.opacity = 1;
        this.style.fontSize = originalSize;
    }

    function handleClick(e) {
        switch (this.textContent) {
            case "GRAVE":
                this.textContent = "MODERADO";
                this.classList.remove('bg-danger');
                this.classList.add('bg-warning');
                break;
            case "MODERADO":
                this.textContent = "LEVE";
                this.classList.remove('bg-warning');
                this.classList.add('bg-success');
                break;
            case "LEVE":
                this.textContent = "DESCONOCIDO";
                this.classList.remove('bg-info');
                this.classList.add('bg-dark');
                break;
            case "DESCONOCIDO":
                this.textContent = "GRAVE";
                this.classList.remove('bg-dark');
                this.classList.add('bg-danger');
                break;
            default:
                this.textContent = "DESCONOCIDO";
                this.classList.remove('bg-dark');
                this.classList.add('bg-danger');
                break;
        }
    }

    let items = document.querySelectorAll('.bt-gravedad');
    items.forEach(function(item) {
        item.addEventListener('mouseover', handleMouseOver, false);
        item.addEventListener('mouseout', handleMouseOut, false);
        item.addEventListener('click', handleClick, false);
    });

    function handleClickable(e) {
        console.log(this);
        if (this.classList.contains('collapsed')) {
            this.classList.remove('collapsed');
        } else {
            this.classList.add('collapsed');
        }
    }

    let clickables = document.querySelectorAll('.accordion-header');
    clickables.forEach(function(clickable) {
        clickable.addEventListener('click', handleClickable, false);
    });

});