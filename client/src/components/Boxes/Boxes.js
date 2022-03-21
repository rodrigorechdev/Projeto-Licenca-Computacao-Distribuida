import './Boxes.css'

import Box from '../Box'

const numberOfUsers = 12

const Boxes = () => {
    return (
        <div className="row boxes-container">
            {Array(numberOfUsers).fill(0).map((val, i) => {
                return (
                    <div className="col-sm-3" key={i}>
                        <Box
                            id={i + 1}
                        />
                    </div>
                )
            })}
        </div>
    )
}

export default Boxes
