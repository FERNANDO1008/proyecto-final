<?php

namespace App;

use Illuminate\Auth\Authenticatable;
use Illuminate\Contracts\Auth\Access\Authorizable as AuthorizableContract;
use Illuminate\Contracts\Auth\Authenticatable as AuthenticatableContract;
use Illuminate\Database\Eloquent\Model;
use Laravel\Lumen\Auth\Authorizable;

class Estacionamiento extends Model
{
    protected $table = 'modelo_estacionamiento';
    protected $primaryKey = 'estacion_id';
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'nombre', 'localizacion', 'longitud', 'latitud', 'calle_primaria', 'calle_secundaria','telefono','celular','estado'
    ];

    // Relación
    public function sitio()
    {
        //relacion de 1 a 1 para traeer la cuenta 
        return $this->hasMany(Sitio::class,'Estacionamiento_id','estacion_id');
    }
    
   
   public $timestamps = false; //se desacctiva 
    /**
     * The attributes excluded from the model's JSON form.
     *
     * @var array
     */
    // protected $hidden = [
    //     'password',
    // ];
}