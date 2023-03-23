window.addEventListener('DOMContentLoaded', event => {
    // Simple-DataTables
    // https://github.com/fiduswriter/Simple-DataTables/wiki

    let datatablesSimple = document.querySelectorAll('.datatablesSimple');
    datatablesSimple.forEach(function(dataTable) {
        console.log('datatablesSimple: ', dataTable);
        if (dataTable) {

            new simpleDatatables.DataTable(dataTable);
        }
    });

});