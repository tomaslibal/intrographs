export function unique<T>(arr: Array<T>): Array<T> {
	return arr.filter((val: T, idx: number, arr: Array<T>) => {
           if (idx > 0 && val === arr[idx-1]) {
               return false;
           } else {
               return true;
           }
        });
}

export function addIfNotFound(array, member) {
    if (array.indexOf(member) === -1) {
        return array.push(member);
    } else {
        return array;
    }
}

export function uniqueValues<T>(array: Array<T>): Array<T> {
    let uniques = [];

    array.forEach(member => {
        if (member.constructor.name === 'Array') {
            let mems = uniqueValues<T>([<T>member]);
            mems.forEach(m => {
                addIfNotFound(uniques, m);
            });
        } else {
            addIfNotFound(uniques, member);
        }
    });

    return uniques;
}

export function shuffleArray(arr) {
    for (var i, tmp, n = arr.length; n; i = Math.floor(Math.random() * n), tmp = arr[--n], arr[n] = arr[i], arr[i] = tmp);
    return arr;
}

export function extendObj(parent: Object, child: Object): Object {
    let obj = Object.create(parent);

    for(let key of Object.keys(child)) {
    	obj[key] = child[key];
    }

    return obj;
}

export function extendPlainObj(parent, child) {
    let obj = parent.constructor();
    Object.keys(parent).forEach(key => {
        obj[key] = parent[key];
    });
    Object.keys(child).forEach(key => {
        obj[key] = child[key];
    });
    return obj;
}
