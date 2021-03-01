<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It is a breeze. Simply tell Lumen the URIs it should respond to
| and give it the Closure to call when that URI is requested.
|
*/

$router->get('/', function () use ($router) {
    return $router->app->version();
});

// router para la comunicacion con el controlador
$router->group(['prefix'=>'persona'],function($router){
    $router->get('','PersonaController@index');
    // $router->get('/listaCliente','ClienteController@index');
    $router->get('/buscarPersona/{cedula}','PersonaController@getPersona');
    $router->post('/inicio','PersonaController@inicioLogin');
    $router->post('','PersonaController@createPersona');
    // $router->delete('/{cedula}','ClienteController@deleteCliente');
    $router->put('/{cedula}','PersonaController@updatePersona');
    $router->put('/perfil/{cedula}','PersonaController@updatePerfil');
    //crear vehiculo
    $router->post('/regVehiculo','PersonaController@createVehiculo');
});
// router para la comunicacion con el controlador
$router->group(['prefix'=>'estacionamiento'],function($router){
    $router->get('','EstacionamientoController@index');
    $router->get('{id}','EstacionamientoController@getEstacionamiento');//busca por id
    $router->post('','EstacionamientoController@createEstacionamiento');//crear un nuevo estacionamiento
    $router->put('{id}','EstacionamientoController@updateEstacionamiento');//modifica estacionamiento
    $router->post('/ticket','EstacionamientoController@ticket');//crear ticket
   
});

//grupo para user
$router->group(['prefix'=>'usuarios'],function($router){
    $router->post('/ingresar','UserController@login');
    $router->post('/salir','UserController@logout');
});