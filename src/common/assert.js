/*
 * stmt bool: statement to be asserted as true
 * msg string: message to be shown if the assertion fails
 * return void
 */
export default function assert(stmt, msg) {
    if (stmt) { return; }

    throw new Error("Assertion Error! " + msg);
}

// alias on the default named export
export { assert as assert };
