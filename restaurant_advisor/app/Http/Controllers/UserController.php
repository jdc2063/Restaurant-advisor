<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Hash;
use App\Models\User;
use Illuminate\Support\Facades\Validator;
use Illuminate\Database\Eloquent\SoftDeletes;

class UserController extends Controller
{

    function Auth(Request $request) {
        $email = $request->email;
        $user = User::where('email', $email)->get();
        if ($request->password == NULL) {
            return response()->json([
                'message' => "Password is missing"
            ], 400);
        }
        if ($user == "[]") {
            return response()->json([
                'message' => "Email not find"
            ], 400);
        }
        $verif = Hash::check($request->password, $user[0]->password);
        if ($verif == true) {
            return response()->json([
                'message' => "Vous êtes identifié",
                'user_id' => $user[0]->id
            ], 200);
        } else {
            return response()->json([
                'message' => "Wrong password"
            ], 400);
        }
    }

    function register(Request $request) {
        $validator = Validator::make($request->all(), [
            'login' => 'required', 
            'password' => 'required',
            'email' => 'required|email|unique:users',
            'name' => 'required',
            'firstname' => 'required',
            'age' => 'required',
        ]);
           
        if ($validator->fails()) {
            return response()->json([
                'message' => "A field is missing",
            ], 400);
        } else {
            $user = User::createDTOtoOBJECT($request);

            $user->save();
            return response($user, 201);
        }
    }

    function getAll() {
        return User::all();
    }
}
