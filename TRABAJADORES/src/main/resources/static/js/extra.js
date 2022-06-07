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
    items.forEach(function (item) {
        item.addEventListener('mouseover', handleMouseOver, false);
        item.addEventListener('mouseout', handleMouseOut, false);
        item.addEventListener('click', handleClick, false);
    });

    function handleClickable(e) {
        //console.log(this);
        if (this.classList.contains('collapsed')) {
            this.classList.remove('collapsed');
        } else {
            this.classList.add('collapsed');
            var id = this.parentElement.id;
            $.ajax({
                //url: '/trabajadores/getSintomas',
                url: "/getSintomas",
                type: "POST",
                data: { 'prd':  id},
                contentType: "application/json",
                success: function (list) {
                    $('#datatable' + id.split("g")[1] + ' tr').remove(); 
                    var result = JSON.stringify(list);
                    var result = JSON.parse(result);
                    let j = 0;
                    $.each(result, function (i, item) {
                        while (j < item.length) {
                            let nombre = item[j].nombre;
                            var rows = "<tr class='list-group-item'>"
                                + "<td>" + nombre + "</td>"
                                + "</tr>";
                            $('#datatable' + id.split("g")[1]).append(rows);
                            j++;
                        }
                    });
                    //setTimeout(function(){location.reload();}, 1500);
                },
                error: function () {

                },
            });
            $.ajax({
                //url: '/trabajadores/getEnfermedades',
                url: "/getEnfermedades",
                type: "POST",
                data: { 'prd':  id},
                contentType: "application/json",
                success: function (list) {
                    $('#datatableE' + id.split("g")[1] + ' tr').remove(); 
                    var result = JSON.stringify(list);
                    var result = JSON.parse(result);
                    let j = 0;
                    $.each(result, function (i, item) {
                        while (j < item.length) {
                            let nombre = item[j].nombre;
                            var rows = "<tr class='list-group-item'>"
                                + "<td>" + nombre + "</td>"
                                + "</tr>";
                            $('#datatableE' + id.split("g")[1]).append(rows);
                            j++;
                        }
                    });
                    //setTimeout(function(){location.reload();}, 1500);
                },
                error: function () {

                },
            });
        }
    }

    let clickables = document.querySelectorAll('.accordion-header');
    clickables.forEach(function (clickable) {
        clickable.addEventListener('click', handleClickable, false);
    });

    function handleClickBotonCita(e) {
        let fila = document.getElementById(this.getAttribute('data-parentid'));
        let num = this.getAttribute('data-parentid').split("g")[1];
        let cate = fila.getElementsByClassName('categoria')[0].textContent;

        $.ajax({
            url: '/trabajadores/requestCita',
            type: "POST",
            data: { 'data': num + "/" + cate },
            contentType: "application/json",
            success: function () {
                $("#success").modal('show');
                setTimeout(function () { location.reload(); }, 1500);
            },
            error: function () {

            },
        });
    }

    let botonesCita = document.querySelectorAll('.citar');
    botonesCita.forEach(function (botonCita) {
        botonCita.addEventListener('click', handleClickBotonCita, false);
    });

});
