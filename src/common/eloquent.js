export function unique(arr) {
	return arr.filter((val, idx, arr) => {
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

export function uniqueValues(array) {
    let uniques = [];

    array.forEach(member => {
        if (member.constructor.name === 'Array') {
            let mems = uniqueValues(member);
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

export function extendObj(parent, child) {
    let obj = Object.create(parent);

    for(let key of Object.keys(child)) {
    	obj[key] = child[key];
    }

    return obj;
}