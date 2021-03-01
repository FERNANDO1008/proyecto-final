<?php

namespace App;

use Illuminate\Auth\Authenticatable;
use Illuminate\Contracts\Auth\Access\Authorizable as AuthorizableContract;
use Illuminate\Contracts\Auth\Authenticatable as AuthenticatableContract;
use Illuminate\Database\Eloquent\Model;
use Laravel\Lumen\Auth\Authorizable;

class Ticket extends Model
{
    protected $table = 'modelo_ticket';
    protected $primaryKey = 'ticket_id';
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'fechaInicio', 'fechaFin', 'costoHora', 'costoTotal', 'horaInicio', 'horaFin'
    ];

    // Relación
    public function sitio()
    {
        //relacion de 1 a 1 para traeer la cuenta 
        return $this->hasOne(Ticket::class,'sitio_id','Sitio_id');
    }
    public function persona()
    {
        //relacion de 1 a 1 para traeer la cuenta 
        return $this->hasOne(Ticket::class,'Persona_id','persona_id');
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