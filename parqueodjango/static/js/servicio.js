var base_url = "http://127.0.0.1:8000/"

//visualizar datos de persona para modificar
function cargardatosPersona(cedula) {
    var url = base_url + "persona/cargarPersona";
    console.log(cedula);
    var cedula = cedula;
    $.ajax({
        url: url,
        dataType: 'json',
        data: 'cedula=' + cedula,
        success: function(data, textStatus, jqXHR) {
            console.log(data.cedula);
            $("#cedulaM").val(data.cedula);
            $("#id_apellidosM").val(data.apellidos);
            $("#id_nombresM").val(data.nombres);
            $("#id_direccionM").val(data.direccion);
            $("#id_telefonoM").val(data.telefono);
            $("#id_celularM").val(data.celular);
        },
        error: function(data) {
            console.log("errror" + data);
        }
    });
};

//busca datos del estacionamiento
function cargardatoEsta(idEstac) {
    if (idEstac != 0) {
        $("#id_Estacionamiento").val(idEstac);
        var url = base_url + "estacionamiento/cargarDatos";
        $.ajax({
            url: url,
            dataType: 'json',
            data: 'idEstac=' + idEstac,
            success: function(data, textStatus, jqXHR) {
                console.log(data);
                $("#localE").val(data.localizacion);
                $("#callepE").val(data.calle_pri);
                $("#callesE").val(data.calle_sec);
                $("#telefonoE").val(data.telefono);
                $("#celularE").val(data.celular);
                var html = '';
                $.each(data.lista, function(index, item) {
                    html += '<tr>';
                    html += ' <td>' + item.numero + '</td>;';
                    if (item.estado) {
                        html += ' <td><span class="badge badge-success"> Disponible </span> </td>;'
                        html += ' <td><div class="btn-group"><a data-toggle="modal" data-target="#ticket" href="#" title="Reservar" class="btn btn-primary btn-sm"  onclick="cargarNum(' + item.sitio_id + ')"><i class="fas fa-map-signs"></i></a></div>&nbsp</td>';
                        html += ' <td><div class="btn-group"><a data-toggle="modal" disabled="true" href="#" title="Detalles inhabilitado" class="btn btn-secondary btn-sm"  ><i class="fas fa-address-card"></i></a></div></td>';
                    } else {
                        html += ' <td><span class="badge badge-danger">Ocupado</span> </td>;'
                        html += ' <td><div class="btn-group"><a data-toggle="modal" href="#" title="Reservar inhabilitado" disabled="true" class="btn btn-secondary btn-sm" ><i class="fas fa-map-signs"></i></a></div>&nbsp</td>';
                        html += ' <td><div class="btn-group"><a data-toggle="modal" data-target="#detalle" href="#" title="Detalle Sitio" class="btn btn-success btn-sm"  onclick="cargarDetalle(' + item.sitio_id + ')"><i class="fas fa-address-card"></i></a></div></td>';
                    }

                    html += '/<tr>';
                });
                $("#tbodySitio").html(html);
            },
            error: function(data) {
                console.log("errror" + data);
            }
        });
    } else {

    }
}

//busca datos del estacionamiento
function cargarSitioTicket(idEstac) {
    if (idEstac != 0) {
        $("#id_Estacionamiento").val(idEstac);
        var url = base_url + "estacionamiento/cargarDatos";
        $.ajax({
            url: url,
            dataType: 'json',
            data: 'idEstac=' + idEstac,
            success: function(data, textStatus, jqXHR) {
                var html = '';
                html += '<select class="form-control" name="sitioEsta" id="sitioEsta"> required="true"';
                // html+='<option value="">--Seleccione---</option>';
                console.log(data.lista.length);
                if (data.lista.length > 0) {
                    $.each(data.lista, function(index, item) {
                        if (item.estado) {
                            html += ' <option value="' + item.sitio_id + '">' + item.numero + '</option>';
                        }
                    });
                } else {
                    msgModal("No hay sitios disponibles", "danger")
                }
                html += '</select>';
                $("#sitioEstacion").html(html);
            },
            error: function(data) {
                console.log("errror" + data);
            }
        });
    } else {

    }
}

function cargarNum(id_sitio) {
    $("#id_sitioTick").val(id_sitio);
}

function cargarDetalle(id_sitio) {
    $("#id_sitioTick1").val(id_sitio);
    var url = base_url + "estacionamiento/cargarDetalle";
    $.ajax({
        url: url,
        dataType: 'json',
        data: 'id_sitio=' + id_sitio,
        success: function(data, textStatus, jqXHR) {
            console.log(data);
            $("#cedulaClie").val(data.cedula);
            $("#nombresClie").val(data.nombres + " " + data.apellidos);
            $("#fechaInicioClie").val(data.fechainic);
            $("#fechaFinClie").val(data.fechafin);
            $("#horaIngresoClie").val(data.horainic);
            $("#horaFinClie").val(data.horafin);
            $("#valorHoraClie").val(data.costoHora);
            $("#costoTotalClie").val(data.costoTotal);
        },
        error: function(data) {
            console.log("errror" + data);
        }
    });
}
//buscar paciente
function buscarPaciente() {
    var url = "http://localhost:3000/buscar_paciente";
    //en el controlador se recibe los mismo datos que estan aqui en el servicio texto = a req.query.texto en el contrlador
    var criterio = $("#criterio").val();
    var texto = $("#buscar").val();
    console.log(criterio + " " + texto);
    $.ajax({
        url: url,
        dataType: 'json',
        data: 'criterio=' + criterio + '&texto=' + texto,
        success: function(data, textStatus, jqXHR) {
            var html = '';
            var cont = 1;
            $.each(data, function(index, item) {
                html += '<tr>';
                html += ' <td>' + cont + '</td>;'
                html += ' <td>' + item.cedula + '</td>;'
                html += ' <td>' + item.paciente + '</td>;'
                html += ' <td>' + item.nro_historia + '</td>;'
                html += ' <td><div class="btn-group"><a href="/modificar/' + item.external_id + '" title="Editar" class="btn btn-primary"><i class="far fa-edit"></i></a></div>&nbsp</td>';
                html += '/<tr>';
                cont++;
            });
            $("#tabla tbody").html(html);
        }
    });
};

/**
 * Permite cargar fecha actual
 */
function cargarFecha() {
    var date = new Date(),
        m = ("0" + (date.getMonth() + 1)).slice(-2),
        d = ("0" + date.getDate()).slice(-2);
    var fecha = ([date.getFullYear(), m, d].join("-"));
    $("#id_fechaInicio").val(fecha);
    $("#id_fechaInicio").attr({
        //    "max" : 10,        // substitute your own
        "min": fecha // values (or variables) here
    });
}

function cargarFechas() {
    var date = new Date(),
        m = ("0" + (date.getMonth() + 1)).slice(-2),
        d = ("0" + date.getDate()).slice(-2);
    var fecha = ([date.getFullYear(), m, d].join("-"));
    $("#id_fechaInicio").val(fecha);
    $("#id_fechaFin").val($("#id_fechaInicio").val());
    $("#id_fechaInicio").attr({
        //    "max" : 10,        // substitute your own
        "min": fecha // values (or variables) here
    });
    $("#id_fechaFin").attr({
        //    "max" : 10,        // substitute your own
        "min": $("#id_fechaInicio").val() // values (or variables) here
    });
    $("#horainic").change(function() {
        console.log($("#horainic").val());

    });
    $("#horafin").change(function() {
        if ($("#horainic").val() != "") {
            console.log($("#horafin").val());
            var hora1 = ("#horainic").split(":"),
                hora2 = ("03:28:56").split(":"),
                t1 = new Date(),
                t2 = new Date();
            t1.setHours(hora1[0], hora1[1], hora1[2]);
            t2.setHours(hora2[0], hora2[1], hora2[2]);

            //Aquí hago la resta
            t1.setHours(t1.getHours() - t2.getHours(), t1.getMinutes() - t2.getMinutes(), t1.getSeconds() - t2.getSeconds());
            // document.body.innerHTML = "La diferencia es de: " + (t1.getHours() ? t1.getHours() + (t1.getHours() > 1 ? " horas" : " hora") : "") + (t1.getMinutes() ? ", " + t1.getMinutes() + (t1.getMinutes() > 1 ? " minutos" : " minuto") : "") + (t1.getSeconds() ? (t1.getHours() || t1.getMinutes() ? " y " : "") + t1.getSeconds() + (t1.getSeconds() > 1 ? " segundos" : " segundo") : "");
        } else {
            mensajePrese("Seleccione una hora de inicio")
        }

    })
}

/**
 * 
 * @param {mensaje de la vista} msg 
 * @param {estado success o danger} estado 
 */
function msgModal(msg, estado) {
    var html = '';
    html = '<div class="modal fade" id="myModalMensaje">';
    html += '<div class="modal-dialog">';
    html += '<div class="modal-content">';
    html += '<div class="modal-header">';
    html += '<h6 class="modal-title">Informacion</h6>';
    html += '<button type="button" class="close" data-dismiss="modal">&times;</button>';
    html += '</div>';
    html += '<div class="modal-body bg-' + estado + '">';
    html += '<strong>' + msg + '</strong>';
    html += '</div>';
    html += '</div>';
    html += '</div>';
    html += '</div>';
    $("#mensajeModal").html(html);
    $("#myModalMensaje").modal("show");
    setTimeout(function() { $('#myModalMensaje').modal('hide'); }, 4000);
}

function mensajePrese(mensajeP) {
    var mensaje = '<div class="alert alert-danger" style="font-size: 15px">';
    mensaje += mensajeP;
    mensaje += '</div>';
    $("#errorF").show();
    $("#errorF").html(mensaje);
    $("#errorF").hide(8000);
}

/*Para validar solo numeros*/
function validaNumero(e) {
    tecla = (document.all) ? e.keyCode : e.which;
    //Tecla de retroceso para borrar, siempre la permite
    if (tecla == 8) {
        return true;
    }
    // Patron de entrada, en este caso solo acepta numeros
    patron = /[0-9]/;
    tecla_final = String.fromCharCode(tecla);
    return patron.test(tecla_final);
}
/*Para validar solo numeros reales*/
function validaNumeroReales(e, input) {
    // Backspace = 8, Enter = 13, ‘0′ = 48, ‘9′ = 57, ‘.’ = 46, ‘-’ = 43
    key = e.keyCode ? e.keyCode : e.which
        // backspace
    if (key == 8) return true
        // 0-9
    if (key > 47 && key < 58) {
        if (field.value == "") return true
        regexp = /.[0-9]{2}$/
        return !(regexp.test(field.value))
    }
    // .
    if (key == 46) {
        if (field.value == "") return false
        regexp = /^[0-9]+$/
        return regexp.test(field.value)
    }
    // other key
    return false
        //     var tecla = (document.all) ? e.keyCode : e.which;
        //     //Tecla de retroceso para borrar, siempre la permite
        //     if (tecla == 8) {
        //         return true;
        //     }
        //     // Patron de entrada, en este caso solo acepta numeros
        //    var patron = /^\d*(\.\d{1})?\d{0,1}$/;
        //     var tecla_final = String.fromCharCode(tecla);
        //     return patron.test(tecla_final);
}

/*Para validar Letras*/
function validaletra(e) {
    tecla = (document.all) ? e.keyCode : e.which;
    //Tecla de retroceso para borrar, siempre la permite
    if (tecla == 8) {
        return true;
    }
    // Patron de entrada, en este caso solo acepta letras y espacio
    patron = /[A-Z a-z]/;
    tecla_final = String.fromCharCode(tecla);
    return patron.test(tecla_final);
}

/*Para validar Solo Mayusculas*/

function validaMayuscula(e) {
    tecla = (document.all) ? e.keyCode : e.which;
    //Tecla de retroceso para borrar, siempre la permite
    if (tecla == 8) {
        return true;
    }
    // Patron de entrada, en este caso solo acepta letras mayusculas
    patron = /[A-Z]/;
    tecla_final = String.fromCharCode(tecla);
    return patron.test(tecla_final);
}

// validacion de cedula
function validarCedula(cedula) {
    var cad = cedula.trim();
    var total = 0;
    var longitud = cad.length;
    var longcheck = longitud - 1;

    if (cad !== "" && longitud === 10) {
        for (i = 0; i < longcheck; i++) {
            if (i % 2 === 0) {
                var aux = cad.charAt(i) * 2;
                if (aux > 9) aux -= 9;
                total += aux;
            } else {
                total += parseInt(cad.charAt(i)); // parseInt o concatenará en
                // lugar de sumar
            }
        }
        total = total % 10 ? 10 - (total % 10) : 0;

        if (cad.charAt(longitud - 1) == total) {
            return true;
        } else {
            return false;
        }
    }
}
// validacion de cedula
function validarCedulaRepetida(cedula) {
    var cedula = cedula.trim();
    var url = base_url + "cedulaRepetida";
    console.log(cedula);
    var flag;
    $.ajax({
        url: url,
        dataType: "json",
        data: "cedula=" + cedula,
        async: false,
        success: function(data, textStatus, jqXHR) {
            flag = data;
        }
    });
    console.log(flag);
    return flag;
}
/**
 * Verifica si el correo esta repetido
 */
function validarCorreoRepetida(correo) {
    var correo = correo.trim();
    var url = base_url + "correoRepetida";
    console.log(correo);
    var flag;
    $.ajax({
        url: url,
        dataType: "json",
        data: "correo=" + correo,
        async: false,
        success: function(data, textStatus, jqXHR) {
            flag = data;
        }
    });
    console.log(flag);
    return flag;
}

/**
 * Validacion de campos registrar y modificar persona
 */
function validar() {
    $.validator.addMethod(
        "validaCedula",
        function(value, element) {
            return this.optional(element) || validarCedula(value);
        },
        "Cedula no valida"
    );
    $.validator.addMethod(
        "cedulaRepetida",
        function(value, element) {
            return this.optional(element) || validarCedulaRepetida(value);
        },
        "Cedula ya registrada"
    );
    $.validator.addMethod(
        "correoRepetida",
        function(value, element) {
            return this.optional(element) || validarCorreoRepetida(value);
        },
        "Correo ya registrado"
    );
    $.validator.addMethod( //override email with django email validator regex - fringe cases: "user@admin.state.in..us" or "name@website.a"
        'email',
        function(value, element) {
            return this.optional(element) || /(^[-!#$%&'*+/=?^_`{}|~0-9A-Z]+(\.[-!#$%&'*+/=?^_`{}|~0-9A-Z]+)*|^"([\001-\010\013\014\016-\037!#-\[\]-\177]|\\[\001-\011\013\014\016-\177])*")@((?:[A-Z0-9](?:[A-Z0-9-]{0,61}[A-Z0-9])?\.)+(?:[A-Z]{2,6}\.?|[A-Z0-9-]{2,}\.?)$)|\[(25[0-5]|2[0-4]\d|[0-1]?\d?\d)(\.(25[0-5]|2[0-4]\d|[0-1]?\d?\d)){3}\]$/i.test(value);
        },
        'Verifica que tienes una dirección de correo electrónico válida.'
    );
    // $.validator.methods.email = function (value, element) {
    //     return this.optional(element) || /[a-z]+@[a-z]+\.[a-z]+/.test(value);
    // };
    $("#idformularioPersona").validate({
        rules: {
            cedula: {
                required: true,
                minlength: 10,
                maxlength: 13,
                validaCedula: true
            }
        },
        messages: {
            cedula: {
                required: "Ingresar un numero de cedula valido",
                minlength: $.validator.format("Necesitamos por lo menos {0} caracteres"),
                maxlength: "{0} caracteres son demasiados!"
            }
        },
        //permite presentar la validacion de los campos de texto
        highlight: function(element) {
            $(element).closest('.form-group').find(".form-control:first").addClass('is-invalid');
        },
        unhighlight: function(element) {
            $(element).closest('.form-group').find(".form-control:first").removeClass('is-invalid');
            $(element).closest('.form-group').find(".form-control:first").addClass('is-valid');

        },
        errorElement: 'span',
        errorClass: 'invalid-feedback',
        errorPlacement: function(error, element) {
            if (element.parent('.input-group').length) {
                error.insertAfter(element.parent());
            } else {
                error.insertAfter(element);
            }
        }
    });
    $("#idformularioPersonaM").validate({

        //permite presentar la validacion de los campos de texto
        highlight: function(element) {
            $(element).closest('.form-group').find(".form-control:first").addClass('is-invalid');
        },
        unhighlight: function(element) {
            $(element).closest('.form-group').find(".form-control:first").removeClass('is-invalid');
            $(element).closest('.form-group').find(".form-control:first").addClass('is-valid');

        },
        errorElement: 'span',
        errorClass: 'invalid-feedback',
        errorPlacement: function(error, element) {
            if (element.parent('.input-group').length) {
                error.insertAfter(element.parent());
            } else {
                error.insertAfter(element);
            }
        }
    });
}

function dataTable() {
    //tabla pedidos data tables para realizar busquedas
    $('#personasTable').DataTable({
        // "dom": "Blfrtip",
        // "buttons": ['excel', 'pdf', 'copy'],
        "language": {
            "lengthMenu": "Mostrar _MENU_ registros por pagina",
            "zeroRecords": "No se encontraron resultados en su busqueda",
            "searchPlaceholder": "Buscar registros",
            "info": "Mostrando registros de _START_ al _END_ de un total de  _TOTAL_ registros",
            "infoEmpty": "No existen registros",
            "infoFiltered": "(filtrado de un total de _MAX_ registros)",
            "search": "Buscar:",
            "paginate": {
                "first": "Primero",
                "last": "Último",
                "next": "Siguiente",
                "previous": "Anterior"
            },
            "aria": {
                "sortAscending": ": ordenar de manera Ascendente",
                "sortDescending": ": ordenar de manera Descendente "
            }
        }

    });
}