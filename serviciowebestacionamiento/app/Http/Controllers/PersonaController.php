<?php

namespace App\Http\Controllers;

use App\Persona;
use App\Cuenta;
use App\Vehiculo;
use App\Http\Helper\ResponseBuilder;
use Illuminate\HTTP\Request; //se debe de importar 
use Laravel\Lumen\Routing\Controller as BaseController;
use Illuminate\Support\Facades\DB;
use ValidarIdentificacion;

class PersonaController extends BaseController
{
    public function inicioLogin(Request $request)
    {
        // $persona   = Persona::all();
        // $persona->load('cuenta'); //permite carfa datos de 1 a 1
        $data = "";
        $existeCorreo = Cuenta::where('correo', $request->correo)->first();
        // $existeCorreo = Cuenta::where('correo', $request->correo)->first();
        if (!empty($existeCorreo)) {
            if ($existeCorreo->clave == $request->clave) {
                $persona = Persona::find($existeCorreo->Persona_id);
                $data = array("cuenta" => $existeCorreo, "perosna" => $persona);
                // $data=(json_encode($existeCorreo)json_encode($persona));
                $status = true; # code...
                $info = "data is successfully";
            } else {
                $status = false; # code...
                $info = "password incorrecto";
            }
        } else {
            $status = false;
            $info = "Data is not successfully";
        }
        return ResponseBuilder::result($status, $info, $data);
    }
    public function index(Request $request)
    {
        // $persona   = Persona::all();
        // $persona->load('cuenta'); //permite carfa datos de 1 a 1
        $persona = Persona::with('cuenta', 'vehiculo')->get();
        //$persona = DB::select("SELECT * FROM modelo_persona INNER JOIN modelo_cuenta ON modelo_persona.persona_id=modelo_cuenta.persona_id");
        // $persona = Persona::all(); //para invocar todo los personas
        // $persona->cuenta;
        if (!empty($persona)) {
            $status = true; # code...
            $info = "data is listed successfully";
        } else {
            $status = false;
            $info = "Data is not listed successfully";
        }
        return ResponseBuilder::result($status, $info, $persona);
        // return response()->json($persona,200);//si lsa respuesta es correcta
    }

    //buscar persona
    public function getPersona(Request $request, $cedula)
    {
        // if ($request->isJson()) {

        // $personas=DB::table('modelo_cuenta')
        // ->join('modelo_persona','modelo_persona.persona_id','=', 'modelo_cuenta.persona_id')
        // ->select('modelo_cuenta.correo','modelo_cuenta.clave','modelo_cuenta.estado', 'modelo_persona.cedula','modelo_persona.nombres','modelo_persona.apellidos');
        $persona = Persona::where('cedula', $cedula)->first();

        if (!empty($persona)) {
            $persona->cuenta;
            $persona->vehiculo;
            $status = true; # code...
            $info = "data is listed successfully";
        } else {
            $status = false;
            $info = "Data is not listed successfully";
        }
        return ResponseBuilder::result($status, $info, $persona);
        // }else{
        //     $status=false;
        //     $info="Unauthorized";
        // }
        // return ResponseBuilder::result($status,$info);
    }

    // crear persona
    public function createPersona(Request $request)
    {
        // Crear nuevo objecto

        $persona = new Persona();
        $cuenta = new Cuenta();
        $vehiculo = new Vehiculo();
        // $validador = new ValidarIdentificacion();
        // if ($validador->validarCedula($request->cedula)) {
        $existepersona = Persona::where('cedula', $request->cedula)->first();
        if (empty($existepersona)) {
            $persona->cedula = $request->cedula;
            $persona->nombres = $request->nombres;
            $persona->apellidos = $request->apellidos;
            $persona->direccion = $request->direccion;
            $persona->telefono = $request->telefono;
            $persona->celular = $request->celular;
            if ($persona->save()) {
                //para crear la cuenta
                $cuenta->clave = $request->clave;
                $cuenta->correo = $request->correo;
                $cuenta->estado = $request->estado;
                // $persona->save();
                $cuenta->persona_id = $persona->persona_id;
                $vehiculo->nro_placa = $request->placa;
                $vehiculo->marca = $request->marca;
                $vehiculo->color = $request->color;
                $vehiculo->persona_id = $persona->persona_id;
                // $cuenta->save();
                if ($cuenta->save() && $vehiculo->save()) {
                    $status = true; # code...
                    $info = "Save successfully";
                }
            } else {
                $status = false;
                $info = "No save successfully";
            }
        } else {
            $status = false;
            $info = "Cedula ya registrada";
            $persona = $existepersona;
        }
        // } else {
        //     $status = false;
        //     $info = "Cedula incorrecta";
        // }
        // strlen(trim($datos->cedula)) > 0 

        return ResponseBuilder::result($status, $info, $persona);
        // return response()->json($persona);
    }

    public function createVehiculo(Request $request)
    {
        $vehiculo = new Vehiculo();
        $persona = Persona::where('cedula', $request->cedula)->first();
        $vehiculoPlaca = Vehiculo::where('nro_placa', $request->nro_placa)->first();
        if (!empty($persona)) {
            if (empty($vehiculoPlaca)) {
                $vehiculo->nro_placa = $request->nro_placa;
                $vehiculo->marca = $request->marca;
                $vehiculo->color = $request->color;
                $vehiculo->Persona_id = $persona->persona_id;
                $vehiculo->save();
                $status = true; # code...
                $info = "Registro exitoso";
            } else {
                $status = false; # code...
                $info = "Nro de placa ya registrado";
            }
        } else {
            $status = false;
            $info = "No se puede realizar el registro";
        }
        return ResponseBuilder::result($status, $info, $vehiculo);
    }
    public function updatePerfil(Request $request, $cedula)
    {
        $persona = Persona::where('cedula', $cedula)->first();
        $persona->cuenta;
        if (!empty($persona)) {
            $persona->nombres = $request->nombres;
            $persona->apellidos = $request->apellidos;
            $persona->direccion = $request->direccion;
            $persona->telefono = $request->telefono;
            $persona->celular = $request->celular;
            $persona->save();
            $persona->cuenta->clave = $request->clave;
            $persona->cuenta->estado = $request->estado;
            $persona->cuenta->save();
            $status = true; # code...
            $info = "Actualizado Correctamente";
        } else {
            $status = false;
            $info = "No se  puede actualizar";
        }
        return ResponseBuilder::result($status, $info, $persona);
    }
    public function updatePersona(Request $request, $cedula)
    {
        $persona = Persona::where('cedula', $cedula)->first();
        $persona->cuenta;
        $persona->vehiculo;
        //$cuenta = Cuenta::where('persona_id', $persona->persona_id)->first();
        // echo ($cuenta);
        if (!empty($persona)) {
            $persona->nombres = $request->nombres;
            $persona->apellidos = $request->apellidos;
            $persona->direccion = $request->direccion;
            $persona->telefono = $request->telefono;
            $persona->celular = $request->celular;
            $persona->save();
            $persona->cuenta->clave = $request->clave;
            // $persona->cuenta->correo = $request->correo;unicos no se puede  modificar
            $persona->cuenta->estado = $request->estado;
            $persona->cuenta->save();
            // $persona->vehiculo->nro_placa = $request->placa;no se puede modificar unicio
            $persona->vehiculo->marca = $request->marca;
            $persona->vehiculo->color = $request->color;
            $persona->vehiculo->save();
            // return response()->json($persona);
            $status = true; # code...
            $info = "persona actualizado Correctamente";
            // persona::destroy($id);
        } else {
            $status = false;
            $info = "No se  puede actualizar persona";
        }
        return ResponseBuilder::result($status, $info, $persona);
    }
}
