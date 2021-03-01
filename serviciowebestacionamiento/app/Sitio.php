<?php

namespace App;

use Illuminate\Auth\Authenticatable;
use Illuminate\Contracts\Auth\Access\Authorizable as AuthorizableContract;
use Illuminate\Contracts\Auth\Authenticatable as AuthenticatableContract;
use Illuminate\Database\Eloquent\Model;
use Laravel\Lumen\Auth\Authorizable;

class Sitio extends Model
{
    protected $table = 'modelo_sitio';
    protected $primaryKey = 'sitio_id';
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'numero', 'estado',
    ];

    // Relación
    public function sitio()
    {
        //relacion de 1 a 1 para traeer la cuenta 
        return $this->belongsTo(Estacionamiento::class);
    }
    public function ticket()
    {
        return $this->belongsTo(Ticket::class);
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