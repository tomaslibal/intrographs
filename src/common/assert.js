export default function assert(stmt) {
    if (stmt) { return; }

    throw new Error("Assertion Error!");
}
