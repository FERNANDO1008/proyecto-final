<?php

namespace App;

use Illuminate\Auth\Authenticatable;
use Illuminate\Contracts\Auth\Access\Authorizable as AuthorizableContract;
use Illuminate\Contracts\Auth\Authenticatable as AuthenticatableContract;
use Illuminate\Database\Eloquent\Model;
use Laravel\Lumen\Auth\Authorizable;

class Vehiculo extends Model
{
    protected $table = 'modelo_vehiculo';
    protected $primaryKey = 'vehiculo_id';
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'nro_placa', 'color', 'marca',
    ];

    // Relación
    public function persona()
    {
        return $this->belongsTo(Persona::class,'persona_id','persona_id');
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
