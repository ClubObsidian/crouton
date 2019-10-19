# crouton
 
A wrapper for running Kotlin coroutines through Java code.

## Usage

``` java

Create a new Crouton.

Crouton crouton = new Crouton();

Then run one of the async methods.

If you need to stop a job that is repeating gracefully call the stop() method.

If you want to stop a job and don't care about completion you can cancel the Crouton from

the job object.

``