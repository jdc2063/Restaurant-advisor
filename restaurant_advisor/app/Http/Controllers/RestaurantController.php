<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Restaurant;
use Illuminate\Support\Facades\Validator;
use Illuminate\Database\Eloquent\SoftDeletes;
class RestaurantController extends Controller
{
    function getAll() {
        return Restaurant::all();
    }

    function getById($id) {
        return Restaurant::find($id);
    }

    function create(Request $request) {
        $validator = Validator::make($request->all(), [
            'name' => 'required', 
            'description' => 'required',
            'grade' => 'required',
            'localization' => 'required',
            'phone_number' => 'required',
            'website' => 'nullable',
            'hours' => 'required',
        ]);
        if ($validator->fails()) {
            return response()->json([
                'message' => "A field is missing"
            ], 400);
        } else {
            $restaurant = Restaurant::createDTOtoOBJECT($request);

            $restaurant->save();
            return response($restaurant, 201);
        }
    }

    function update(Request $request, $id) {
        $restaurant = Restaurant::find($id);
        
        if ($restaurant) {
            $restaurant = Restaurant::updateDTOtoOBJECT($request, $restaurant);
            if ($restaurant == "400") {
                return response()->json([
                    'message' => "Name restaurant already exist."
                ], 400); 
            }
            $restaurant->save();

            return response($restaurant, 200);
        } else {
            return response()->json([
                'message' => "Restaurant doesn't exist."
            ], 400); 
        }
        
    }

    function delete($id) {
        $restaurant = Restaurant::find($id);

        if ($restaurant) {
            $restaurant->delete();
        } else {
            return response()->json([
                'message' => "Restaurant doesn't exist."
            ], 400); 
        }
    
    }
    

}
