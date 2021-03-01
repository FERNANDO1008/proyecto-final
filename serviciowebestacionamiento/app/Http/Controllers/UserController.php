<?php

namespace App\Http\Controllers;

use App\User;
use App\Http\Helper\ResponseBuilder;
use Illuminate\Support\Facades\Hash;
use Illuminate\Hashing\BcryptHasher;
use Illuminate\HTTP\Request;//se debe de importar 
use Laravel\Lumen\Routing\Controller as BaseController;

class UserController extends BaseController
{
    //se define los controles como ver cliente, transacciones ver cuentas,,, lo que se deceee..
    public function login(Request $request){
        $username=$request->username;
        $password=$request->password;
        $user=User::where('username',$username)->first();
        // echo $user;
        error_log($this->django_password_verify($password,$user->password));
        error_log($user->password);
        if (!empty($user) || strlen($user)>-1) {
            if ($this->django_password_verify($password,$user->password)) {
                $status=true;
                $info="User is correcto";
            }else{
                $status=false;
                $info="User is incorrecto";
            }
        }else {
            $status=true;
            $info="User is incorrecto";
        }
        return ResponseBuilder::result($status,$info,$user);
    }
    //metodo para desencriiptar clave
    public function django_password_verify(string $password,string $djangoHash):bool{
        $piece=\explode('$',$djangoHash);
        if (count($piece)!==4) {
            throw new Exception("Ilegal formato");
        }
        list($header,$iter,$salt,$hash)=$piece;
        if (preg_match('#^pbkdf2_([a-z0-9A-Z]+)$#',$header,$m)) {
            $algo=$m[1];
        }else {
            throw new Exception(\sprintf("Bad header (%s)", $header));
        }
        if (!in_array($algo,hash_algos())) {
            throw new Exception(\sprintf("Ilegal hash algorithm (%s)",$algo));
        }
        //hash_pbkdf2 genera una derivacion de la clave PBKDF2 de una contraseña proporcionada
        //algo es el nombre del algoritmo hash seleccionado (esto es 'md5','sha256','haval160,4)
        $calc=hash_pbkdf2(
            $algo,
            $password,
            $salt,
            (int)$iter,
            32,
            true
        );
        return \hash_equals($calc,\base64_decode($hash));
    }
    public function logout(Request $request){
        $request->user()::destroy();
        $status=true;
        $info="Session close";
        return ResponseBuilder::result($status,$info);
    }
     
}
