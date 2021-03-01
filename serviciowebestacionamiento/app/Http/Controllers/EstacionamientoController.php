<?php

namespace App\Http\Controllers;

use App\Estacionamiento;
use App\Sitio;
use App\Ticket;
use App\Http\Helper\ResponseBuilder;
use Illuminate\HTTP\Request; //se debe de importar 
use Laravel\Lumen\Routing\Controller as BaseController;
use Illuminate\Support\Facades\DB;
use ValidarIdentificacion;

class EstacionamientoController extends BaseController
{
    public function index(Request $request)
    {
        $estac = Estacionamiento::with('sitio')->get();

        if (!empty($estac)) {
            $status = true; # code...
            $info = "data is listed successfully";
        } else {
            $status = false;
            $info = "Data is not listed successfully";
        }
        return ResponseBuilder::result($status, $info, $estac);
        // return response()->json($estacionamiento,200);//si lsa respuesta es correcta
    }

    //buscar estacionamiento
    public function getEstacionamiento(Request $request, $id)
    {
        $estacionamiento = Estacionamiento::find($id);
        $estacionamiento->sitio;
        if (!empty($estacionamiento)) {
            $status = true; # code...
            $info = "data is listed successfully";
        } else {
            $status = false;
            $info = "Data is not listed successfully";
        }
        return ResponseBuilder::result($status, $info, $estacionamiento);
        // }else{
        //     $status=false;
        //     $info="Unauthorized";
        // }
        // return ResponseBuilder::result($status,$info);
    }

    // crear estacionamiento
    public function createEstacionamiento(Request $request)
    {
        $estacionamiento = new estacionamiento();
        $estacionamiento->nombre = $request->nombre;
        $estacionamiento->localizacion = $request->localizacion;
        $estacionamiento->longitud = $request->longitud;
        $estacionamiento->latitud = $request->latitud;
        $estacionamiento->calle_primaria = $request->calle_primaria;
        $estacionamiento->calle_secundaria = $request->calle_secundaria;
        $estacionamiento->telefono = $request->telefono;
        $estacionamiento->celular = $request->celular;
        $estacionamiento->estado = 1;
        if ($estacionamiento->save()) {
            $status = true; # code...
            $info = "Save successfully";
        } else {
            $status = false;
            $info = "No save successfully";
        }
        // } else {
        //     $status = false;
        //     $info = "id incorrecta";
        // }
        // strlen(trim($datos->id)) > 0 

        return ResponseBuilder::result($status, $info, $estacionamiento);
        // return response()->json($estacionamiento);
    }

    public function updateEstacionamiento(Request $request, $id)
    {
        $estacionamiento = estacionamiento::find($id);
        if (!empty($estacionamiento)) {
            $estacionamiento->nombre = $request->nombre;
            $estacionamiento->localizacion = $request->localizacion;
            $estacionamiento->longitud = $request->longitud;
            $estacionamiento->latitud = $request->latitud;
            $estacionamiento->calle_primaria = $request->calle_primaria;
            $estacionamiento->calle_secundaria = $request->calle_secundaria;
            $estacionamiento->telefono = $request->telefono;
            $estacionamiento->celular = $request->celular;
            $estacionamiento->estado = $request->estado;
            if ($estacionamiento->save()) {
                $status = true; # code...
                $info = "estacionamiento actualizado Correctamente";
            } else {
                $status = false; # code...
                $info = "estacionamiento no actualizado";
            }
        } else {
            $status = false;
            $info = "No existe registro de estacionamiento";
        }
        return ResponseBuilder::result($status, $info, $estacionamiento);
    }
    public function ticket(Request $request)
    {
        $tick = new Ticket();
        $existerReserva = Ticket::where('Persona_id', $request->Persona_id)->first();
        $horaini=( $request->horaInicio).".000000";
        // if ($existerReserva->fechaInicio === $request->fechaInicio && $existerReserva->Persona_id === $request->Persona_id && $existerReserva->fechaInicio===$request->horaInicio) {
        if ($existerReserva->Persona_id == $request->Persona_id && $existerReserva->fechaInicio == $request->fechaInicio && $existerReserva->horaInicio==$horaini) {
            $status = false; # code...
            $info = "Ya tiene una reserva pendiente ";
        } else {
            $tick->fechaInicio = $request->fechaInicio;
            $tick->fechaFin = $request->fechaFin;
            $tick->costoHora = $request->costoHora;
            $tick->costoTotal = $request->costoTotal;
            $tick->horaInicio = $request->horaInicio;
            $tick->horaFin = $request->horaFin;
            $tick->Persona_id = $request->Persona_id;
            $tick->Sitio_id = $request->Sitio_id;
            if ($tick->save()) {
                $status = true; # code...
                $info = "Reserva exitosa";
            } else {
                $status = false; # code...
                $info = "No se pudo realizar la reserva";
            }
        }
        return ResponseBuilder::result($status, $info, $tick);
    }
}
