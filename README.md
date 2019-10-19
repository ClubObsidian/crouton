# crouton
 
A wrapper for running Kotlin coroutines through Java code.

## Usage

Create a new Crouton.

`Crouton crouton = new Crouton();`

Then run one of the async methods.

If you want to stop a `Job` gracefully call the `stop()` method.

If you want to stop a `Job` and don't care about completion you can cancel the Crouton from

the `Job` object.
