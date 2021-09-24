declare var game: Game; 

export interface Console {
    log(...data: any[]): void;
}

interface VectorMovement {
    getVector: Vector3f;
    setVector(data: Vector3f) : void;
}

export interface Player extends VectorMovement {
    getName: string;
}

export interface Entity extends VectorMovement {
    getEntityName: string;
    deleteEntity: void;
}

export interface Sound {
    getName: string;
    getID: number;
}

export interface Event {
    getName: string;
    getFunctionName: string;
}

export interface Callbacks {
    setKeyCallback(data: string);
    setWindowResizedCallback(data: string);
}

export interface Camera {
    setFOV(data: number);
    setYaw(data: number);
    setRoll(data: number);
    setPitch(data: number);
    getYaw : number;
    getRoll : number;
    getPitch : number;
    getFOV: number;
}

export interface Audio {
    play(data: string) : number;
    playVector(data: Vector3f) : number;
    stopMusic(data: number) : void;
    getPlayedSounds : Sound[];
}

export interface Vector3f {
    getX: number;
    getY: number;
    getZ: number;
}



export interface Game {
    getVersion: string;
    getInterpretterEngine: string;
    getScriptNativeAccessVersion: string;
    getName: string;
    getEntityById: Entity;
    addEntity(data: Entity) : void;
    addEvent(eventType: string, listenerName: string);
    getEvents : Event[];
    getPlayers: Player[];
    console: Console;
    getEvents: Event[];
    setEvent(data: string, functionName: string);
    audio : Audio;
    getCamera : Camera;
    callbacks: Callbacks;
    exitGame: void;
}