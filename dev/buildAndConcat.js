var rollup = require('rollup').rollup;
var babel = require('rollup-plugin-babel');

var fs = require('fs');

rollup({
  entry: 'src/main.js',
  format: 'umd',
  plugins: [
    babel({
      include: ['src/**'],
      exclude: ['build/**', 'dist/**', 'dev/**', 'node_modules/**', 'test/**'],
      sourceMap: false 
    })
  ]
}).then(function(bundle) { 

    const generated = bundle.generate();
    const code = generated.code;

    fs.writeFileSync('dist/out.raw.js', code);

    console.log('Build done. Output saved @ dist/out.raw.js');
}).catch(function(reason) {
    console.log('Build failed');
    console.log(reason);
});
