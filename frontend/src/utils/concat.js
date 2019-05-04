const concat = (...args) => args.reduce((acc, val) => [...acc, ...val])

export default concat