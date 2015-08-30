export default function assert(stmt) {
    if (stmt) { return; }

    throw new Error("Assertion Error!");
}

// alias on the default named export
export { assert as assert };
