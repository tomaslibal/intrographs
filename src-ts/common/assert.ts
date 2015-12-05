/*
 * stmt bool: statement to be asserted as true
 * msg string: message to be shown if the assertion fails
 * return void
 */
export function assert(stmt: any, msg?: string) {
    if (stmt) { return; }

    throw new Error(`Assertion Error! ${msg}`);
}
