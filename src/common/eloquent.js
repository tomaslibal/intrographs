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