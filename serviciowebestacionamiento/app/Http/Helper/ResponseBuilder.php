<?php
namespace App\Http\Helper;

class ResponseBuilder {
    public static function result($status="",$info="",$data=""){
        return [
            "success"=>$status,
            "informacion"=>$info,
            "data"=>$data,
        ];
    }
}


?>