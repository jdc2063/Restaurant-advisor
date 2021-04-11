<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\RestaurantController;
use App\Http\Controllers\MenuController;
use App\Http\Controllers\UserController;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::get('/', function () {
    return response()->json([
        'message' => 'This is th officiel Restaurant Advisor API'
    ]);
});

#Utilisateur
Route::post('/register', [UserController::class, 'register']);
Route::post('/auth', [UserController::class, 'Auth']);
Route::get('/users', [UserController::class, 'getAll']);

#Affiche les restaurants
Route::get('/restaurants', [RestaurantController::class, 'getAll']);
Route::get('/restaurants/{id}', [RestaurantController::class, 'getByID']);
Route::post('/restaurants', [RestaurantController::class, 'create']);
Route::delete('/restaurants/{id}', [RestaurantController::class, 'delete']);
Route::put('/restaurants/{id}', [RestaurantController::class, 'update']);

#Affiche les menus
Route::get('/menus', [MenuController::class, 'getAll']);
Route::get('/menus/{id}', [MenuController::class, 'getByID']);
Route::get('/restaurants/{id}/menus', [MenuController::class, 'getAllfromRest']);
Route::post('/restaurants/{id}/menus', [MenuController::class, 'create']);
Route::put('/menus/{id}', [MenuController::class, 'update']);
Route::delete('/menus/{id}', [MenuController::class, 'delete']);


Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});
